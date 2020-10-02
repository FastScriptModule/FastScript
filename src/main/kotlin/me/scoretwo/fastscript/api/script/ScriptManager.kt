package me.scoretwo.fastscript.api.script

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.config.SettingConfig
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
        defaultScriptPath.listFiles()?.forEach { loadScript(it) }

        for (file in SettingConfig.instance.scriptPaths) {
            if (file.isDirectory) {
                file.listFiles()?.forEach { loadScript(it) }
            } else {
                loadScript(file)
            }
        }


    }

}