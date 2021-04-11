package me.scoretwo.fastscript.api.script.custom

import me.scoretwo.fastscript.api.script.ScriptOption
import me.scoretwo.utils.bukkit.configuration.yaml.ConfigurationSection
import me.scoretwo.utils.bukkit.configuration.yaml.MemorySection
import me.scoretwo.utils.bukkit.configuration.yaml.file.YamlConfiguration
import me.scoretwo.utils.bukkit.configuration.yaml.patchs.ignoreCase
import me.scoretwo.utils.bukkit.configuration.yaml.patchs.ignoreCase
import me.scoretwo.utils.bukkit.configuration.yaml.patchs.loadConfiguration
import java.io.File

open class ConfigScriptOption(val file: File? = null, val config: YamlConfiguration = file?.loadConfiguration() ?: YamlConfiguration().also { it.set("main", "main") }): ScriptOption {
    override var main: String = config.getString(config.ignoreCase("main")) ?: "main"
    override var meta = mutableMapOf<String, Any?>().also { map ->
        config.getStringList(config.ignoreCase("meta"))?.forEach {
            map[it.substringBefore(":")] = it.substringAfter(":")
        }
        config.getKeys(true).forEach {
            if (!arrayOf("main", "meta").contains(it.toLowerCase()))
                map[it.toLowerCase()] = config.getConfigurationSection(config.ignoreCase(it))
        }
    }

    fun reload() {
        main = config.getString(config.ignoreCase("main")) ?: "main"
        meta = mutableMapOf<String, Any?>().also { map ->
            config.getStringList(config.ignoreCase("meta"))?.forEach {
                map[it.substringBefore(":")] = it.substringAfter(":")
            }
            config.getKeys(true).forEach {
                if (!arrayOf("main", "meta").contains(it.toLowerCase()))
                    map[it.toLowerCase()] = config.getConfigurationSection(config.ignoreCase(it))
            }
        }
    }

    fun getSection(): ConfigurationSection = config

    fun getSection(path: String): ConfigurationSection {
        val raw = meta[path]
        if (raw is ConfigurationSection) return raw
        return YamlConfiguration()
    }

}