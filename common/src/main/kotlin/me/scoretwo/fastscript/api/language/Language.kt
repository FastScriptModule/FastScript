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

    val defaultConfig = YamlConfiguration().also {
        it["FORMAT-HEADER"] = YamlConfiguration().also {
            it["INFO"] = "&7[&3Fast&bScript&7] &bINFO &8| &7"
            it["WARN"] = "&7[&3Fast&bScript&7] &eWARN &8| &7"
            it["ERROR"] = "&7[&3Fast&bScript&7] &cERROR &8| &7"
            it["TIPS"] = "&7[&3Fast&bScript&7] &2TIPS &8| &7"
            it["HOOKED"] = "&7[&3Fast&bScript&7] &6HOOKED &8| &7"
            it["DEBUG"] = "&7[&3Fast&bScript&7] &3DEBUG &8| &7"
        }
        it["SUBSTANTIVE"] = YamlConfiguration().also {
            it["ARGS"] = "args"
            it["USAGE"] = "usage"
            it["EVALUATED"] = "evaluated"
        }
        it["EXEC-ID"] = YamlConfiguration().also {
            it["CONFIGS"] = "configs"
            it["LIBS"] = "libs"
            it["SCRIPT-MANAGER"] = "script manager"
            it["EXPANSION-MANAGER"] = "expansion manager"
            it["COMMAND-NEXUS"] = "CommandNexus"
        }
        it["DOWNLOADED-LIB"] = "Downloaded &b{lib_name}&7...&8({millisecond}ms)"
        it["DOWNLOAD-LIBS-FAILED"] = "&cFailed to download the pre-library file, please check your network, FastScript failed to start."
        it["HOOKED-PLUGIN"] = "Hook to the plugin &e{plugin_name} &7successfully, now you can use the relevant functions of this plugin!"
        it["COMMAND-NEXUS"] = YamlConfiguration().also {
            it["TIPS"] = YamlConfiguration().also {
                it["ONLY-CONSOLE"] = "&cThis command can only be executed on the console."
                it["ONLY-PLAYER"] = "&cThis command can only be executed by the player."
                it["NO-PERMISSION"] = "&cYou do not have permission to execute the command."
                it["UNKNOWN-USAGE"] = "&cUsage &e{usage_error} &cis incorrect, you may want to use &e{usage_guess}&c."
            }
            it["HELPER"] = YamlConfiguration().also {
                it["NOT-FOUND-COMMANDS"] = "&7No command data available."
                it["CLICK-INSERT-COMMAND"] = "&7Click insert command: &f/{command}"
                it["CLICK-TO-GO-URL"] = "&7Click to go to url: &f{url}"
                it["PLAYER-IS-OFFLINE"] = "&7Player &a{player_name} &7is not online"
            }
            it["COMMANDS"] = YamlConfiguration().also {
                it["MIGRATE"] = YamlConfiguration().also {
                    it["DESCRIPTION"] = "Migrate from other plugins."

                    it["UNKNOWN-ACTION"] = "No action found {action_name}"
                }

                it["EXPANSION"] = YamlConfiguration().also {
                    it["NOT-FOUND-NAME-OR-SIGN"] = "Cannot find the extension name or sign &c{expansion_name}&7! Please check the name."
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
                            "  &7About expansions &6{expansion_name} &7more help, please enter:"

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

                        it["EXECUTE-RESULT"] = "Execute script &b{script_name} &7used expansion &6{expansion_name} &7to return result: {result}"
                    }
                    it["EVALUATE"] = YamlConfiguration().also {
                        it["DESCRIPTION"] = "Evaluate the specified content to get the return value (':s' added at the end does not return a message)"

                        it["EXECUTE-RESULT"] = "Evaluate script &b{script_name} &7used expansion &6{expansion_name} &7to return result: {result}"
                    }
                    it["RELOAD"] = YamlConfiguration().also {
                        it["DESCRIPTION"] = "Reload this script."
                        it["RELOADED-SCRIPT"] = "Reloaded script &b{script_name} &7successful!"
                    }

                    it["INFO"] = YamlConfiguration().also {
                        it["DESCRIPTION"] = "View the information of this script."
                        it["TITLE"] = "Script &b{script_name}&7's information."
                        it["TEXTS"] = mutableListOf(
                            "  &3&l* &7Version: &2{script_version}",
                            "  &3&l* &7Authors: &3{script_authors}",
                            "  &3&l* &7Description: &f{script_description}",
                            "  &3&l* &7Main: &f{script_main}",
                            "  &3&l* &7Bind expansions: &6{script_bind_expansions}",
                            "",
                            "  &7About script &6{script_name} &7more help, please enter:"

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

                    it["ASYNC-LOADED-ALL"] = "Async loaded all settings successful."
                    it["ASYNC-LOADED-CONFIG"] = "Async loaded configuration file successful."
                    it["ASYNC-LOADED-SCRIPT"] = "Async loaded script file successful."
                    it["ASYNC-LOADED-PLUGIN"] = "Async loaded plugin settings successful."
                }
                it["TOOLS"] = YamlConfiguration().also {
                    it["DESCRIPTION"] = "Some useful tool libraries."

                    it["COMMAND"] = YamlConfiguration().also {
                        it["DESCRIPTION"] = "Send a command as sender('@CONSOLE' means console)."
                    }

                    it["BUKKIT"] = YamlConfiguration().also {
                        it["SOUNDS"] = YamlConfiguration().also {
                            it["DESCRIPTION"] = "Play Sound to the player."
                            it["NOT-FOUND-SOUND"] = "Sound &8{sound_name} &7not found."
                        }
                    }
                }
                it["DEBUG"] = YamlConfiguration().also {
                    it["DESCRIPTION"] = "Used to start debugging or view some debugging information."
                }
            }
        }
        it["EXPANSION"] = YamlConfiguration().also {
            it["TYPE-ENGINE"] = YamlConfiguration().also {
                it["EVALUATE-SCRIPT-ERROR"] = "An error occurred during script {script_name} evaluation, please check the script format. reason: \n&8{reason}"
                it["EVALUATE-TEMP-SCRIPT-ERROR"] = "An error occurred during temp script evaluation, please check the script format. reason: \n&8{reason}"
                it["EXECUTE-SCRIPT-ERROR"] = "An error occurred when the script {script_name} executes the function {execute_main}, please check the script format. reason: \n&8{reason}"
                it["EXECUTE-SCRIPT-FUNCTION-NOT-FOUND-ERROR"] = "An error occurred when the script {script_name} executes the function {execute_main}, reason: &8this function not found!"
                it["EXECUTE-TEMP-SCRIPT-ERROR"] = "An error occurred when the temp script executes the function {execute_main}, please check the script format, reason: \n&8{reason}"
                it["EXECUTE-SCRIPT-FUNCTION-NOT-FOUND-ERROR"] = "An error occurred when the temp script executes the function {execute_main}, reason: &8this function not found!"
            }
            it["ERROR-BY-CAUSE"] = YamlConfiguration().also {
                it["LOAD-ERROR"] = "An exception occurred while loading expansion {file_name}, reason: \n&8{reason}"
                it["LOAD-DESCRIPTION-FILE-ERROR"] = "An error occurred while loading the expansion '{file_name}' description file, reason: \n&8{reason}"
                it["LOAD-MAIN-CLASS-ERROR"] = "An error occurred while loading the main class {description_main} of expansion '{file_name}', reason: \n&8{reason}"
                it["LOAD-MAIN-CLASS-MAIN-NOT-DEPEND"] = "An error occurred while loading the main class {description_main} of expansion '{file_name}', reason: &cThe main class does not depend on FastScriptExpansion."
                it["CAN-NOT-LOAD-MAIN-CLASS"] = "Unable to load the extension '{file_name}' because it has no FastScriptExpansion class available!"
            }
        }
        it["SCRIPT"] = YamlConfiguration().also {
            it["PROCESS-RESULT"] = YamlConfiguration().also {
                it["SCRIPT-OPTION-FILE-NOT-EXISTS"] = "Script option file exists!"
                it["SCRIPT-TYPE-NOT-SUPPORTED"] = "The script file extension is not supported!"
                it["SCRIPT-FILE-NAME-CANNOT-SPACES"] = "File name cannot contain spaces!"
            }
            it["SCRIPT-FAILED-LOAD-BY-PROCESS-RESULT"] = "An error occurred while loading script &3{file_name}&7, reason: &8{reason}"
        }
        it["LOADED-COUNTS-PROCESS-SUCCESS"] = "Loaded &b{total} &7{id}, &a{success} &7successes.&8({millisecond}ms)"
        it["LOADED-COUNTS-PROCESS-SUCCESS-HAS-FAILED"] = "Loaded &b{total} &7{id}, &a{success} &7successes, &c{fail} &7failures.&8({millisecond}ms)"


        it["INVOKE"] = YamlConfiguration().also {
            it["ASYNC-SUCCESS"] = "Async {exec_type} {exec_name}.&8({millisecond}ms)"
            it["ASYNC-SUCCESS-HAS-DESCRIPTION"] = "Async {exec_type} {exec_name}, {exec_description}.&8({millisecond}ms)"
            it["ASYNC-FAILED"] = "&cAsync failed to invoke {exec_name}, reason:\n&8{reason}"

            it["SUCCESS"] = "{exec_type} {exec_name}.&8({millisecond}ms)"
            it["SUCCESS-HAS-DESCRIPTION"] = "{exec_type} {exec_name}, {exec_description}.&8({millisecond}ms)"
            it["FAILED"] = "&cFailed to invoke {exec_name}, reason:\n&8{reason}"
        }

        it["FILE-LISTENER"] = YamlConfiguration().also {
            it["SCRIPT"] = YamlConfiguration().also {
                it["LOADED"] = "Detected file &6{file_name} &7changes, successfully reloaded script &b{script_name}&7.&8({millisecond}ms)"
            }
        }

        it["VERSION"] = version

    }

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
