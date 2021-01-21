package me.scoretwo.fastscript.api.script

import me.scoretwo.utils.bukkit.configuration.yaml.ConfigurationSection
import me.scoretwo.utils.bukkit.configuration.yaml.file.YamlConfiguration
import me.scoretwo.utils.bukkit.configuration.yaml.patchs.getLowerCaseNode
import java.io.File

abstract class ScriptOptions(val file: File, val config: YamlConfiguration) {
    val main: String = config.getString(config.getLowerCaseNode("main"))
    val meta = mutableMapOf<String, String>().also { map ->
        config.getStringList(config.getLowerCaseNode("meta")).forEach {
            map[it.substringBefore(":")] = it.substringAfter(":")
        }
    }

    val otherSection = mutableMapOf<String, ConfigurationSection>().also { sections ->
        config.getKeys(false).forEach {
            if (!arrayOf("main", "meta").contains(it.toLowerCase()))
                sections[it] = config.getConfigurationSection(it)
        }
    }

}