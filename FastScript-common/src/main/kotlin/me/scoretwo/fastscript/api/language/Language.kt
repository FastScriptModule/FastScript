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

            if (version == null || languages.version != version) {
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
        it["SUBSTANTIVE"] = YamlConfiguration().also {
            it["ARGS"] = "args"
            it["USAGE"] = "usage"
            it["EVALUATED"] = "EVALUATED"
        }
        it["COMMAND-NEXUS"] = YamlConfiguration().also {
            it["TIPS"] = YamlConfiguration().also {
                it["ONLY-CONSOLE"] = "This command can only be executed on the console."
                it["ONLY-PLAYER"] = "This command can only be executed by the player."
                it["NO-PERMISSION"] = "You do not have permission to execute the command."
                it["UNKNOWN-USAGE"] = "&cUsage &e{usage_error} &cis incorrect, you may want to use &e{usage_guess}&c."
            }
            it["HELPER"] = YamlConfiguration().also {
                it["NOT-FOUND-COMMANDS"] = "&7No command data available."
                it["CLICK-INSERT-COMMAND"] = "&7Click insert command: &f/{command}"
                it["CLICK-TO-GO-URL"] = "&7Click to go to url: &f{url}"
            }
            it["COMMANDS"] = YamlConfiguration().also {
                it["EXPANSION"] = YamlConfiguration().also {
                    it["NOT-FOUND-NAME-OR-SIGN"] = "Cannot find the extension name or sign §c{expansion_name}§7! Please check the name."
                    it["LOADED-EXPANSIONS"] = "Currently available extensions: &6{expansions}"
                    it["DESCRIPTION"] = "View all expansions or operations."
                    it["SUB-EXPANSION-DESCRIPTION"] = "About {expansion_name} commands."

                    it["INFO"] = YamlConfiguration().also {
                        it["DESCRIPTION"] = "View the information of this expansion."
                        it["TITLE"] = "Expansion &6{expansion_name}&7's information."
                        it["TEXTS"] = mutableListOf(
                            "  &3&l* &7Sign: &a{expansion_sign}",
                            "  &3&l* &7File suffix: &a{expansion_file_suffix}",
                            "  &3&l* &7Bind scripts: &f{expansion_bind_scripts}",
                            "",
                            "  &7About expansions &6{expansion_name} &7more help, please enter:&8(Bad grammar)"

                        )
                    }
                    it["EVALUATE"] = YamlConfiguration().also {
                        it["DESCRIPTION"] = "Evaluate the specified content to get the return value (':s' added at the end does not return a message)"
                        it["EVALUATE-RESULT"] = "Use extension &6{expansion_name} &7to evaluate the result of the content: &b{result}"
                    }
                }
                it["SCRIPT"] = YamlConfiguration().also {
                    it["DESCRIPTION"] = "View the information of this script."
                    it["NOT-FOUND-SCRIPT"] = "Not found script &b{script_name}&7! Please check the name."
                    it["SUB-SCRIPT-DESCRIPTION"] = "About {script_name} commands."

                    it["EXECUTE"] = YamlConfiguration().also {
                        it["DESCRIPTION"] = "Execute the specified content to get the return value (':s' added at the end does not return a message)"

                        it["EXECUTE-RESULT"] = "Execute script &b{script_name} &7used expansion &6{expansion_name} to return result: {result}"
                    }
                    it["EVALUATE"] = YamlConfiguration().also {
                        it["DESCRIPTION"] = "Evaluate the specified content to get the return value (':s' added at the end does not return a message)"

                        it["EXECUTE-RESULT"] = "Evaluate script &b{script_name} &7used expansion &6{expansion_name} to return result: {result}"
                    }
                    it["RELOAD"] = YamlConfiguration().also {
                        it["DESCRIPTION"] = "Reload this script."
                        it["RELOADED-SCRIPT"] = "Reloaded script &b{script_name} &7successful!"
                    }

                    it["INFO"] = YamlConfiguration().also {
                        it["DESCRIPTION"] = "View the information of this expansion."
                        it["TITLE"] = "Script &b{script_name}&7's information."
                        it["TEXTS"] = mutableListOf(
                            "  &3&l* &7Version: &2{script_version}",
                            "  &3&l* &7Authors: &3{script_authors}",
                            "  &3&l* &7Description: &f{script_description}",
                            "  &3&l* &7Main: &f{script_main}",
                            "  &3&l* &7Bind expansions: &6{script_bind_expansions}",
                            "",
                            "  &7About script &6{script_name} &7more help, please enter:&8(Bad grammar)"

                        )
                    }

                }
                it["RELOAD"] = YamlConfiguration().also {
                    it["DESCRIPTION"] = "Reload configuration files or settings."
                    it["MODE"] = YamlConfiguration().also {
                        it["ALL"] = "Reload all."
                        it["CONFIG"] = "Reload configuration file."
                        it["SCRIPT"] = "Reload script file."
                        it["PLUGIN"] = "Reload plugin settings."
                    }
                    it["LOADED-ALL"] = "Loaded all settings successful."
                    it["LOADED-CONFIG"] = "Loaded configuration file successful."
                    it["LOADED-SCRIPT"] = "Loaded script file successful."
                    it["LOADED-PLUGIN"] = "Loaded plugin settings successful."
                }
            }

        }
        it["EXPANSION"] = YamlConfiguration().also {
            it["TYPE-ENGINE"] = YamlConfiguration().also {
                it["EVALUATE-SCRIPT-ERROR"] = "An error occurred during script {script_name} evaluation, please check the script format."
                it["EVALUATE-TEMP-SCRIPT-ERROR"] = "An error occurred during temp script evaluation, please check the script format."
                it["EXECUTE-SCRIPT-ERROR"] = "An error occurred when the script {script_name} executes the function {execute_main}, please check the script format."
                it["EXECUTE-SCRIPT-FUNCTION-NOT-FOUND-ERROR"] = "An error occurred when the script {script_name} executes the function {execute_main}, this function not found!"
                it["EXECUTE-TEMP-SCRIPT-ERROR"] = "An error occurred when the temp script executes the function {execute_main}, please check the script format."
                it["EXECUTE-SCRIPT-FUNCTION-NOT-FOUND-ERROR"] = "An error occurred when the temp script executes the function {execute_main}, this function not found!"
            }
        }
        it["SCRIPT-MANAGER"] = YamlConfiguration().also {
            it["PROCESS-RESULT"] = YamlConfiguration().also {
                it["SCRIPT-OPTION-FILE-NOT-EXISTS"] = "Script option file exists!"
                it["SCRIPT-TYPE-NOT-SUPPORTED"] = "The script file extension is not supported!"
                it["SCRIPT-FILE-NAME-CANNOT-SPACES"] = "File name cannot contain spaces!"
            }
            it["SCRIPT-FAILED-LOAD-BY-PROCESS-RESULT"] = "An error occurred while loading script {file_name}, reason: &8{reason}"
        }
        it["LOADED-PROCESS-SUCCESS"] = "Loaded &b{total} &7{id}, &a{success} &7successes.&8({millisecond}ms)"
        it["LOADED-PROCESS-HAS-FAILED"] = "Loaded &b{total} &7{id}, &a{success} &7successes, &c{fail} &7failures.&8({millisecond}ms)"

        it["VERSION"] = languages.version

    }

    operator fun set(node: String, any: Any?) = config.set(node.toUpperCase(), any)

    operator fun get(node: String) = config.getString(node.toUpperCase())!!

    fun getList(node: String): MutableList<String> = config.getStringList(node.toUpperCase())!!

}
