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

    private val optionFiles =

    fun getScript(name: String) = scripts[name]

    fun loadScript(file: File) {
        FastScript.instance.expansionManager.expansions.forEach {

            // 載入脚本
            scripts[file.name.substringBeforeLast(".")]
        }

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
        defaultScriptPath.mkdirs()
        defaultScriptPath.listFiles()?.forEach { loadScript(it) }

        settings.getStringList(settings.getLowerCaseNode("load-script-files")).forEach {
            val file = File(it)

            if (file.exists()) selectScriptFiles(file).forEach { loadScript(it) }

        }
    }

    fun isConfigScriptOption(section: ConfigurationSection) =
        section.isString(section.getLowerCaseNode("name")) &&
        (section.isString(section.getLowerCaseNode("version")) || section.isInt(section.getLowerCaseNode("version"))) &&
        section.isString(section.getLowerCaseNode("main"))

    fun selectScriptFiles(file: File): MutableList<File> = mutableListOf<File>().let { files ->
        if (file.isDirectory) {
            file.listFiles()?.forEach {
                files.addAll(selectScriptFiles(it))
            }
        } else if (file.name.endsWith(".yml", true)) {
            files.add(file)
        }
        files
    }


    fun loadFromFolderScript(folder: File): ProcessResult {
        val optionFile: File = arrayOf(
            "option.yml",
            "${folder.name}.yml",
            "setting.yml"
        ).let {
            for (fileName in it) {
                val file = File(fileName)
                if (file.exists()) return@let file
            }

            return ProcessResult(ProcessResultType.FAILED, "Option file not found in ${folder.name}.")
        }
        val option = ScriptOptions(optionFile)
        val script = Script(ScriptDescription.fromSection(option.config), option)


        val scriptFiles = mutableListOf<File>().also {

        }


    }


}