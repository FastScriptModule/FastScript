package me.scoretwo.fastscript.api.script

import me.scoretwo.fastscript.*
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.api.script.custom.ConfigScriptOption
import me.scoretwo.fastscript.api.script.custom.CustomScript
import me.scoretwo.fastscript.api.script.temp.TempScript
import me.scoretwo.fastscript.api.utils.process.ProcessResult
import me.scoretwo.fastscript.api.utils.process.ProcessResultType
import me.scoretwo.fastscript.listeners.ScriptFileListener
import me.scoretwo.utils.bukkit.configuration.yaml.ConfigurationSection
import me.scoretwo.utils.bukkit.configuration.yaml.patchs.ignoreCase
import me.scoretwo.utils.sender.GlobalPlayer
import me.scoretwo.utils.sender.GlobalSender
import me.scoretwo.utils.server.task.TaskType
import org.apache.commons.io.monitor.FileAlterationListener
import org.apache.commons.io.monitor.FileAlterationMonitor
import org.apache.commons.io.monitor.FileAlterationObserver
import java.io.File

class ScriptManager {

    private val folders = mutableMapOf<File, ScriptFileListener>()

    val scripts = mutableMapOf<String, CustomScript>()

    // bStats
    var evaluateCount = 0
    var executeCount = 0

    var operationCount = 0

    val defaultFolder: File get() {
        folders.forEach {
            return it.key
        }
        return null!!
    }

    private fun addFolder(folder: File) {
        folders[folder] = ScriptFileListener()
    }

    init {
        addFolder(File(plugin.dataFolder, "scripts"))
        if (!defaultFolder.exists()) {
            defaultFolder.mkdirs()
            File(defaultFolder, "example.js").writeText(
                """
                function main() {
                    sender.sendMessage("§athis is demo.")
                }
                """.trimIndent()
            )
        }
    }

    val tempScripts = mutableMapOf<String, TempScript>()

    fun getScript(name: String) = scripts[name]

    private fun loadGeneralScript(scriptFile: File): Pair<CustomScript?, ProcessResult> {
        val scriptName = scriptFile.name.substringBeforeLast(".")
        scriptFile.parentFile.listFiles()?.forEach {
            if (it.name == "$scriptName.yml") {
                return Pair(null, ProcessResult(ProcessResultType.OTHER, languages["SCRIPT.PROCESS-RESULT.SCRIPT-OPTION-FILE-NOT-EXISTS"], "file" to scriptFile.name))
            }
        }

        val file = let {
            FastScript.instance.expansionManager.expansions.forEach { expansion ->
                if (!scriptFile.name.endsWith(expansion.fileSuffix))
                    return@forEach

                return@let scriptFile
            }
            return Pair(null, ProcessResult(ProcessResultType.FAILED, languages["SCRIPT.PROCESS-RESULT.SCRIPT-TYPE-NOT-SUPPORTED"], "file" to scriptFile.name))
        }
        val script = CustomScript(
/*            object : ScriptDescription {
                override val name: String = scriptName
                override val main: String = "main"
                override val version: String? = null
                override val description: String? = null
                override val authors: Array<String> = arrayOf()

            },*/
            scriptName,
            ConfigScriptOption(),
            mutableListOf(file)
        ).reload()

        scripts[scriptName] = script
        return Pair(script, ProcessResult(ProcessResultType.SUCCESS))
    }

    /**
     * 仅接受文件后缀为yml的文件或者可用的脚本文件夹才能被处理
     */
    private fun loadScript(file: File): Pair<CustomScript?, ProcessResult> {
        if (file.name.contains(" ")) return Pair(null, ProcessResult(ProcessResultType.FAILED, languages["SCRIPT.PROCESS-RESULT.SCRIPT-FILE-NAME-CANNOT-SPACES"]))
        if (file.isDirectory) {
            return loadFromFolderScript(file)
        }

        val scriptName = if (file.name.endsWith(".yml"))
            file.name.substringBeforeLast(".")
        else
            return loadGeneralScript(file)

        val option = ConfigScriptOption(file)
        val script = CustomScript(option.getString("name") ?: scriptName, option).reload()

        script.scriptFiles = mutableListOf<File>().also { files ->
            FastScript.instance.expansionManager.expansions.forEach { expansion ->
                val candidateFile = File(file.parentFile, "$scriptName.${expansion.fileSuffix}")
                if (candidateFile.exists()) {
                    files.add(candidateFile)
                }
            }
        }

        script.texts.keys.forEach { sign ->
            val expansion = FastScript.instance.expansionManager.getExpansionBySign(sign) ?: return@forEach

/*            if (expansion.needEval) {
                expansion.eval(sign, plugin.server.console)
            }
*/
        }

        scripts[file.name.substringBeforeLast(".")] = script
        return Pair(script, ProcessResult(ProcessResultType.SUCCESS))
    }

    private fun loadFromFolderScript(folder: File): Pair<CustomScript?, ProcessResult> {
        val optionsFiles = arrayOf("option.yml", "${folder.name}.yml", "setting.yml")

        val optionsFile: File = optionsFiles.let {
            for (fileName in it) {
                val file = File(folder, fileName)
                if (file.exists()) return@let file
            }

            return Pair(null, ProcessResult(ProcessResultType.FAILED, "Option file not found in ${folder.name}."))
        }
        val option = ConfigScriptOption(optionsFile)
        val script = CustomScript(option.getString("name") ?: folder.name, option).reload()

        script.scriptFiles = mutableListOf<File>().also { files ->
            folder.listFiles()?.forEach { file ->
                FastScript.instance.expansionManager.expansions.forEach {
                    if (file.name.endsWith(it.fileSuffix))
                        files.add(file)
                }
            }
        }

/*
        script.bindExpansions().forEach {
            if (it.needEval)
                script.eval(it.sign, plugin.server.console)
        }
*/

        scripts[folder.name] = script

        return Pair(script, ProcessResult(ProcessResultType.SUCCESS))
    }

    fun loadScripts() {
        val startTime = System.currentTimeMillis()
        var total = 0
        var success = 0
        var fail = 0

        // unload fileListener
        folders.forEach {
            it.value.monitor?.stop()
        }

        val protects = mutableMapOf<String, CustomScript>()
        scripts.forEach {
            if (it.value.init.protected && it.value.configOption.file?.exists() == true) {
                protects[it.key] = it.value
                return@forEach
            }
            it.value.bindExpansions().forEach { expansion ->
                it.value.execute(expansion, plugin.server.console, "unload")
            }
            it.value.unregisterListeners()
        }
        scripts.clear()
        total += protects.size

        settings.getStringList(settings.ignoreCase("load-script-files")).forEach {
            addFolder(File(it))
        }

        folders.keys.forEach { file ->
            if (file.isDirectory && file.exists()) file.listFiles()?.forEach {
                loadScript(it).also {
                    total++
                    if (it.second.type == ProcessResultType.FAILED || it.first == null) {
                        fail++
                        plugin.server.console.sendMessage(FormatHeader.ERROR, languages["SCRIPT.SCRIPT-FAILED-LOAD-BY-PROCESS-RESULT"].setPlaceholder(
                            mapOf("file_name" to (it.second.infos["file"] ?: "Unknown"), "reason" to it.second.message)
                        ))
                    }
                    else if (it.second.type == ProcessResultType.SUCCESS)
                        success++
                }
            }
        }

        if (settings.getBoolean(settings.ignoreCase("options.file-listener"))) {
            folders.forEach {
                val observer = FileAlterationObserver(it.key)
                observer.addListener(it.value)
                val monitor = FileAlterationMonitor(100L, observer)
                it.value.observer = observer
                it.value.monitor = monitor
                monitor.start()
            }
        }

        val placeholders = mapOf(
            "id" to "scripts",
            "total" to "$total",
            "success" to "$success",
            "fail" to "$fail",
            "millisecond" to "${System.currentTimeMillis() - startTime}"
        )
        if (fail == 0)
            plugin.server.console.sendMessage(FormatHeader.INFO, languages["LOADED-COUNTS-PROCESS-SUCCESS"].setPlaceholder(placeholders))
        else
            plugin.server.console.sendMessage(FormatHeader.INFO, languages["LOADED-COUNTS-PROCESS-SUCCESS-HAS-FAILED"].setPlaceholder(placeholders))
    }

    fun isConfigScriptOption(section: ConfigurationSection) =
        section.isString(section.ignoreCase("name")) &&
        (section.isString(section.ignoreCase("version")) || section.isInt(section.ignoreCase("version"))) &&
        section.isString(section.ignoreCase("main"))



    fun eval(script: CustomScript, sign: String, sender: GlobalSender, vararg args: String) =
        script.eval(sign, sender, *args).also { evaluateCount += 1; operationCount += 1 }

    fun execute(script: CustomScript, sign: String, sender: GlobalSender, main: String = script.configOption.main, args: Array<Any?> = arrayOf()) =
        script.execute(sign, sender, main, args).also { executeCount += 1; operationCount += 1 }


    fun eval(script: String, sign: String, sender: GlobalSender, vararg args: String) = let {
        eval(getScript(script) ?: return@let null, sign, sender, *args)
    }

    fun execute(script: String, sign: String, sender: GlobalSender, main: String = getScript(script)?.configOption?.main ?: "main", args: Array<Any?> = arrayOf()) = let {
        execute(getScript(script) ?: return@let null, sign, sender, main, args)
    }

    fun setPlaceholder(player: GlobalPlayer, text: String) = plugin.setPlaceholder(player, text)

    fun setPlaceholder(sender: Any, text: String) = plugin.setPlaceholder(plugin.toGlobalPlayer(sender), text)
}