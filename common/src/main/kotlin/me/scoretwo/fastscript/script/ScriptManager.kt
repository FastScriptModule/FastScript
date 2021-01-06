package me.scoretwo.fastscript.script

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.script.Script
import me.scoretwo.fastscript.config.SettingConfig
import me.scoretwo.utils.bukkit.configuration.yaml.patchs.getLowerCaseNode
import java.io.File

class ScriptManager {

    val defaultScriptPath = File(FastScript.instance.dataFolder, "scripts")

    val scripts = mutableListOf<Script>()

    fun getScript(name: String): Script? {
        for (script in scripts) {
            if (script.name == name) return script
        }
        return null
    }

    fun loadScript(file: File) {
        scripts.add(CustomScript(file))
    }

    /**
    * 暂时同步, 异步以后写
    */
    @Synchronized
    fun loadScripts() {
        scripts.clear()
        defaultScriptPath.mkdirs()
        defaultScriptPath.listFiles()?.forEach { loadScript(it) }

        SettingConfig.instance.getStringList(SettingConfig.instance.getLowerCaseNode("load-script-files")).forEach {
            val file = File(it)

            if (file.exists()) selectScriptFiles(file).forEach { loadScript(it) }

        }
    }

    /**
     * 选取脚本文件
     * 该创意来源于 TrMenu
     * @author Arasple
     */
    fun selectScriptFiles(file: File): MutableList<File> =
        mutableListOf<File>().let { files ->
            if (file.isDirectory) {
                file.listFiles()?.forEach {
                    files.addAll(selectScriptFiles(it))
                }
            } else if (!file.name.startsWith("#") && file.name.endsWith(".js", true)) {
                files.add(file)
            }
            return@let files
        }

}