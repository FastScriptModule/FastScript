package me.scoretwo.fastscript.config

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.plugin
import me.scoretwo.fastscript.script.options.ScriptOption
import me.scoretwo.fastscript.utils.Utils
import me.scoretwo.utils.bukkit.configuration.yaml.patchs.getLowerCaseNode
import me.scoretwo.utils.syntaxes.StreamUtils
import java.io.File

class SettingConfig(file: File): Config(file) {

    lateinit var defaultScriptOption: ScriptOption

    lateinit var defaultLanguage: MessageConfig

    init {
        instance = this
        onReload()
    }

    override fun onReload() {
        load(file)

        FastScript.instance.initLanguageFiles()
        defaultScriptOption = ScriptOption.fromConfig(getConfigurationSection(getLowerCaseNode("default-script-options"))!!)
        defaultLanguage = MessageConfig(File(
                "${plugin.dataFolder}/languages",
                "${getString(getLowerCaseNode("options.language"))}.yml"))
    }

    companion object {
        lateinit var instance: SettingConfig

        fun init() {
            val file = File(plugin.dataFolder, "settings.yml")

            Utils.saveDefaultResource(file, StreamUtils.getResource("settings.yml")!!)

            SettingConfig(file)
        }
    }

}