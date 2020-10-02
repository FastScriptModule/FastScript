package me.scoretwo.fastscript.config

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.script.ScriptOption
import me.scoretwo.fastscript.api.yaml.YAMLObject
import me.scoretwo.fastscript.utils.FileUtils
import java.io.File

class SettingConfig: Config(File(FastScript.instance.dataFolder, "settings.yml")) {

    lateinit var defaultScriptOption: ScriptOption

    val scriptPaths = mutableListOf<File>()

    init {
        instance = this
        onReload()
    }

    override fun onReload() {
        setMap(loadConfiguration(file))
        for (s in getLowerCaseYAMLArray("load-script-files")) { if (s is String) { scriptPaths.add(File(s)) } }
        defaultScriptOption = ScriptOption.fromConfigSection(getLowerCaseYAMLObject("default-script-options"))
    }

    companion object {
        lateinit var instance: SettingConfig
    }

}