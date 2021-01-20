package me.scoretwo.fastscript.config

import me.scoretwo.fastscript.plugin
import me.scoretwo.utils.bukkit.configuration.yaml.file.YamlConfiguration
import me.scoretwo.utils.bukkit.configuration.yaml.patchs.saveConfiguration
import java.io.File

class SettingConfig(): Config(File(plugin.dataFolder, "settings.yml")) {

    val default = YamlConfiguration().also {
        it.set("Options", YamlConfiguration().also {
            it.set("Debug", false)
            it.set("Language", "en_Us")
            it.set("File-Listener", true)
        })
        it.set("Load-Script-Files", listOf("plugins/CustomScriptFolder"))
        it.set("Default-Script-Options", YamlConfiguration().also {
            it.set("Main", "main")
            it.set("Meta", listOf("key:value"))
        })
    }

    override fun onReload() {
        if (!file.exists()) {
            file.parentFile.mkdirs()
            file.saveConfiguration(default)
        }
    }

}