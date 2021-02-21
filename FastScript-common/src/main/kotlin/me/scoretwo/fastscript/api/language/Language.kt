package me.scoretwo.fastscript.api.language

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.languages
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
        } else {
            config.load(file)
            val version = config.getString(config.getLowerCaseNode("version"))

            if (version == null || languages.version.toString() != version) {
                languages.defaultLanguage.config.getKeys(true).forEach { if (!config.contains(it)) { config.set(it, languages.defaultLanguage.config[it]) } }
                config.save(file)
            }

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
        it["FORMAT-HEADER"] = YamlConfiguration().also {
            it.set("INFO", "&7[&3Fast&bScript&7] &bINFO &8| &7")
            it.set("WARN", "&7[&3Fast&bScript&7] &eWARN &8| &7")
            it.set("ERROR", "&7[&3Fast&bScript&7] &cERROR &8| &7")
            it.set("TIPS", "&7[&3Fast&bScript&7] &2TIPS &8| &7")
            it.set("HOOKED", "&7[&3Fast&bScript&7] &6HOOKED &8| &7")
            it.set("DEBUG", "&7[&3Fast&bScript&7] &3DEBUG &8| &7")
        }
        it["COMMAND-NEXUS"] = YamlConfiguration().also {
            it["SUBSTANTIVE"] = YamlConfiguration().also {
                it["ARGS"] = "args"
            }
            it["TIPS"] = YamlConfiguration().also {
                it["ONLY-CONSOLE"] = "This command can only be executed on the console."
                it["ONLY-PLAYER"] = "This command can only be executed by the player."
                it["NO-PERMISSION"] = "You do not have permission to execute the command."
                it["UNKNOWN-USAGE"] = "&cUsage &e{usage_error} &cis incorrect, you may want to use &e{usage_guess}"
            }
            it["HELP-GENERATION"] = YamlConfiguration().also {
                it["NOT-FOUND-COMMANDS"] = "&7No command data available."
                it["CLICK-INSERT-COMMAND"] = "&7Click insert command: &f/{command}"
                it["CLICK-TO-GO-URL"] = "&7Click to go to url: &f{url}"
            }
            it["COMMANDS"] = YamlConfiguration().also {

            }
        }

    }

    operator fun set(node: String, any: Any?) = config.set(node.toUpperCase(), any)

    operator fun get(node: String) = config.getString(node.toUpperCase())!!

    fun getList(node: String): MutableList<String> = config.getStringList(node.toUpperCase())!!

}
