package me.scoretwo.fastscript.api.language

import me.scoretwo.fastscript.languages
import me.scoretwo.fastscript.plugin
import me.scoretwo.utils.bukkit.configuration.yaml.file.YamlConfiguration
import me.scoretwo.utils.bukkit.configuration.yaml.patchs.ignoreCase
import me.scoretwo.utils.bukkit.configuration.yaml.patchs.saveConfiguration
import me.scoretwo.utils.server.task.TaskType
import java.io.File
import java.util.concurrent.TimeUnit

class Language(val version: String, val name: String = "en_US") {

    val file: File = File(plugin.dataFolder, "language/$name.yml")

    var defaultConfig = en_US

    val config = YamlConfiguration()

    operator fun set(node: String, any: Any?) = config.set(node.toUpperCase(), any)

    operator fun get(node: String) = config.getString(node.toUpperCase()) ?: defaultConfig.getString(node.toUpperCase())!!

    fun getList(node: String): MutableList<String> = config.getStringList(node.toUpperCase())!!

    fun reload() = this.also {
        if (!file.exists()) {
            file.saveConfiguration(defaultConfig)
        } else {
            config.load(file)
            val configVersion = config.getString(config.ignoreCase("version"))

            if (configVersion == null || version != configVersion) {
                defaultConfig.getKeys(true).forEach { if (!config.contains(it)) { config.set(it, defaultConfig[it]) } }
                config.save(file)
            }

        }
    }

}
