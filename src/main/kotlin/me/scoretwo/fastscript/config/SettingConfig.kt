package me.scoretwo.fastscript.config

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.script.options.ScriptOption
import me.scoretwo.utils.configuration.patchs.getLowerCaseNode
import me.scoretwo.utils.language.save
import java.io.File

class SettingConfig(file: File): Config(file) {

    lateinit var defaultScriptOption: ScriptOption

    lateinit var defaultLanguage: MessageConfig

    val scriptPaths = mutableListOf<File>()

    init {
        instance = this
        onReload()
    }

    override fun onReload() {
        load(file)
        for (s in getStringList(getLowerCaseNode("load-script-files"))) {
            scriptPaths.add(File(s))
        }

        defaultScriptOption = ScriptOption.fromConfig(getConfigurationSection(getLowerCaseNode("default-script-options"))!!)
        defaultLanguage = MessageConfig(File(
                "${FastScript.instance.dataFolder}/languages",
                "${getString(getLowerCaseNode("options.language"))}.yml"))
    }

    companion object {
        lateinit var instance: SettingConfig

        fun init() {
            val file = File(FastScript.instance.dataFolder, "settings.yml")

            if (!file.exists()) {
                file.save(FastScript.instance.classLoader.getResourceAsStream("/settings.yml")!!)
            }

            SettingConfig(file)
        }
    }

}