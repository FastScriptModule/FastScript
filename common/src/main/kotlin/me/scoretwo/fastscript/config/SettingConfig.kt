package me.scoretwo.fastscript.config

import me.scoretwo.fastscript.plugin
import me.scoretwo.utils.bukkit.configuration.yaml.file.YamlConfiguration
import me.scoretwo.utils.bukkit.configuration.yaml.patchs.ignoreCase
import java.io.File
import java.util.*

class SettingConfig: Config(File(plugin.dataFolder, "settings.yml")) {

    val version = "2"

    val defaultConfig = applyConfig(YamlConfiguration())

    fun applyConfig(cfg: YamlConfiguration): YamlConfiguration {
        cfg["Options"] = YamlConfiguration().also {
            it["Debug"] = false
            it["Language"] = "${Locale.getDefault()}"
            it["File-Listener"] = true
            it["Internal-Expansions"] = YamlConfiguration().also {
                it["JavaScript"] = true
                it["Scala"] = false
            }
        }
        cfg["Load-Script-Files"] = listOf("plugins/CustomScriptFolder")
        cfg["Default-Script-Options"] = YamlConfiguration().also {
            it["Main"] = "main"
            it["Meta"] = listOf("key:value")
        }
        cfg["Version"] = version
        return cfg
    }

    init {
        applyConfig(this)
    }

    override fun onReload() {
        if (getString(ignoreCase("version")) ?: "" != version) {
            defaultConfig.getKeys(true).forEach { if (!this.contains(it)) {
                this[it] = defaultConfig[it]
            } }
            save(file)
        }
    }

}