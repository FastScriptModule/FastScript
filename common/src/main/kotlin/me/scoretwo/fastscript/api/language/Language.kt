package me.scoretwo.fastscript.api.language

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.plugin
import me.scoretwo.utils.bukkit.configuration.yaml.file.YamlConfiguration
import me.scoretwo.utils.bukkit.configuration.yaml.patchs.getLowerCaseNode
import me.scoretwo.utils.bukkit.configuration.yaml.patchs.saveConfiguration
import java.io.File

class Language {

    val name: String
    val file: File

    constructor(name: String = "en_US") {
        this.name = name
        this.file = File(plugin.dataFolder, "language/$name.yml")
        if (!file.exists()) {
            file.saveConfiguration(config)
        }
    }

    constructor(file: File) {
        this.name = file.name.substringBeforeLast(".")
        this.file = file
        if (!file.exists()) {
            file.saveConfiguration(config)
        }
    }

    val config = YamlConfiguration().also {
        it.addDefault("FORMAT-HEADER", YamlConfiguration().also {
            it.addDefault("INFO", "&7[&2Fast&aScript&7] &bINFO &8| &7")
            it.addDefault("WARN", "&7[&2Fast&aScript&7] &eWARN &8| &7")
            it.addDefault("ERROR", "&7[&2Fast&aScript&7] &cERROR &8| &7")
            it.addDefault("TIPS", "&7[&2Fast&aScript&7] &2TIPS &8| &7")
            it.addDefault("HOOKED", "&7[&2Fast&aScript&7] &6HOOKED &8| &7")
            it.addDefault("DEBUG", "&7[&2Fast&aScript&7] &3DEBUG &8| &7")
        })

    }

    operator fun set(node: String, any: Any?) = config.set(
        let {
            if (config.contains(config.getLowerCaseNode(node))) {
                config.getLowerCaseNode(node)
            } else {
                node.toUpperCase()
            }
        }
        , any)

    operator fun get(node: String) = config.getString(config.getLowerCaseNode(node))!!

    fun getList(node: String) = config.getStringList(config.getLowerCaseNode(node))!!

}
