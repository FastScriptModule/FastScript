package me.scoretwo.fastscript.api.script

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.plugin
import me.scoretwo.fastscript.settings
import me.scoretwo.utils.bukkit.configuration.yaml.patchs.getLowerCaseNode
import java.io.File

class ScriptManager {

    val defaultScriptPath = File(plugin.dataFolder, "scripts")

    val scripts = mutableMapOf<String, Script>()

    fun getScript(name: String) = if (scripts.containsKey(name)) scripts[name] else null

    fun loadScript(file: File) {
        FastScript.instance.expansionManager.expansions.forEach {

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

    fun selectScriptFiles(file: File): MutableList<File> = mutableListOf<File>().let { files ->
        if (file.isDirectory) {
            file.listFiles()?.forEach {
                files.addAll(selectScriptFiles(it))
            }
        } else if (file.name.endsWith(".js", true)) {
            files.add(file)
        }
        files
    }

}