package me.scoretwo.fastscript.api.script

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.utils.process.ProcessResult
import me.scoretwo.fastscript.api.utils.process.ProcessResultType
import me.scoretwo.fastscript.plugin
import me.scoretwo.fastscript.settings
import me.scoretwo.utils.bukkit.configuration.yaml.ConfigurationSection
import me.scoretwo.utils.bukkit.configuration.yaml.patchs.getLowerCaseNode
import java.io.File

class ScriptManager {

    val folders = mutableListOf(File(plugin.dataFolder, "scripts"))

    val scripts = mutableMapOf<String, Script>()

    fun getScript(name: String) = scripts[name]

    /**
     * 仅接受文件后缀为yml的文件或者可用的脚本文件夹才能被处理
     */
    private fun loadScript(file: File): ProcessResult {
        if (file.name.contains(" ")) return ProcessResult(ProcessResultType.FAILED, "File name cannot contain spaces!")
        if (file.isDirectory) {
            return loadFromFolderScript(file)
        }

        val scriptName= if (file.name.endsWith(".yml"))
            file.name.substringBeforeLast(".")
        else
            return ProcessResult(ProcessResultType.OTHER, "The file does not belong to the script, skip reading!")

        val options = ScriptOptions(file)
        val script = Script(ScriptDescription.fromSection(options.config), options)

        script.scriptFiles = mutableListOf<File>().also { files ->
            FastScript.instance.expansionManager.expansions.forEach { expansion ->
                val candidateFile = File(file.parentFile, "$scriptName.${expansion.fileSuffix}")
                if (candidateFile.exists()) {
                    files.add(candidateFile)
                }
            }
        }

        scripts[file.name.substringBeforeLast(".")] = script
        return ProcessResult(ProcessResultType.SUCCESS)
    }

    private fun loadFromFolderScript(folder: File): ProcessResult {
        val optionsFiles = arrayOf("option.yml", "${folder.name}.yml", "setting.yml")

        val optionsFile: File = optionsFiles.let {
            for (fileName in it) {
                val file = File(fileName)
                if (file.exists()) return@let file
            }

            return ProcessResult(ProcessResultType.FAILED, "Option file not found in ${folder.name}.")
        }
        val options = ScriptOptions(optionsFile)
        val script = Script(ScriptDescription.fromSection(options.config), options)

        script.scriptFiles = mutableListOf<File>().also { files ->
            folder.listFiles()?.forEach { file ->
                script.scriptProcessor.forEach {
                    if (file.endsWith(it.value.expansion.fileSuffix)) files.add(file)
                }
            }
        }
        scripts[folder.name] = script

        return ProcessResult(ProcessResultType.SUCCESS)
    }
    // {
    //    scripts.add(CustomScript(file))
    // }

    /**
    * 暂时同步, 异步以后写
    */
    @Synchronized
    fun loadScripts() {
        scripts.clear()
        folders[0].mkdirs()
        folders[0].listFiles()?.forEach { loadScript(it) }

        settings.getStringList(settings.getLowerCaseNode("load-script-files")).forEach {
            val file = File(it)

            if (file.isDirectory && file.exists()) file.listFiles()?.forEach { loadScript(it) }

        }
    }

    fun isConfigScriptOption(section: ConfigurationSection) =
        section.isString(section.getLowerCaseNode("name")) &&
        (section.isString(section.getLowerCaseNode("version")) || section.isInt(section.getLowerCaseNode("version"))) &&
        section.isString(section.getLowerCaseNode("main"))





}