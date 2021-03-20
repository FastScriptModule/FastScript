package me.scoretwo.fastscript.api.language

import me.scoretwo.fastscript.languages
import me.scoretwo.utils.bukkit.configuration.yaml.file.YamlConfiguration

/**
 * @author 83669
 * @date 2021/3/18 21:52
 *
 * @project FastScript
 */

val zh_CN get() = YamlConfiguration().also {
    it["FORMAT-HEADER"] = YamlConfiguration().also {
        it["INFO"] = "&7[&3Fast&bScript&7] &b信息 &8| &7"
        it["WARN"] = "&7[&3Fast&bScript&7] &e警告 &8| &7"
        it["ERROR"] = "&7[&3Fast&bScript&7] &c错误 &8| &7"
        it["TIPS"] = "&7[&3Fast&bScript&7] &2提示 &8| &7"
        it["HOOKED"] = "&7[&3Fast&bScript&7] &6挂钩 &8| &7"
        it["DEBUG"] = "&7[&3Fast&bScript&7] &3调试 &8| &7"
    }
    it["SUBSTANTIVE"] = YamlConfiguration().also {
        it["ARGS"] = "参数"
        it["USAGE"] = "用法"
        it["EVALUATED"] = "评估成功"
        it["ENABLED"] = "启用"
        it["DISABLED"] = "禁用"
    }
    it["EXEC-ID"] = YamlConfiguration().also {
        it["CONFIGS"] = "配置文件"
        it["LIBS"] = "运行库"
        it["SCRIPT-MANAGER"] = "脚本管理器"
        it["EXPANSION-MANAGER"] = "拓展管理器"
        it["COMMAND-NEXUS"] = "命令中枢"
    }
    it["DOWNLOADED-LIB"] = "正在下载 &b{lib_name}&7...&8({millisecond}ms)"
    it["DOWNLOAD-LIBS-FAILED"] = "&c无法下载运行库文件, 请检查您的网络, FastScript 无法启动."
    it["HOOKED-PLUGIN"] = "成功挂钩插件 &e{plugin_name} &7, 现在您可以使用该插件的相关功能!"
    it["COMMAND-NEXUS"] = YamlConfiguration().also {
        it["TIPS"] = YamlConfiguration().also {
            it["ONLY-CONSOLE"] = "&c该命令只能在控制台上执行."
            it["ONLY-PLAYER"] = "&c该命令只能由玩家执行."
            it["NO-PERMISSION"] = "&c您没有该命令的权限."
            it["UNKNOWN-USAGE"] = "&c当前用法 &e{usage_error} &c不正确, 正确用法 &e{usage_guess}&c."
        }
        it["HELPER"] = YamlConfiguration().also {
            it["NOT-FOUND-COMMANDS"] = "&7没有可用的命令数据."
            it["CLICK-INSERT-COMMAND"] = "&7点击插入命令: &f/{command}"
            it["CLICK-TO-GO-URL"] = "&7点击进入网页: &f{url}"
            it["PLAYER-IS-OFFLINE"] = "&7目标玩家 &a{player_name} &7不在线"
        }
        it["COMMANDS"] = YamlConfiguration().also {
            it["MIGRATE"] = YamlConfiguration().also {
                it["DESCRIPTION"] = "从其它插件迁移."

                it["UNKNOWN-ACTION"] = "未知迁移动作 {action_name}"
            }

            it["EXPANSION"] = YamlConfiguration().also {
                it["NOT-FOUND-NAME-OR-SIGN"] = "找不到拓展指定的名称或标识 &c{expansion_name}&7! 请检查名称或标识是否正确."
                it["LOADED-EXPANSIONS"] = "当前可用的拓展: &6{expansions}"
                it["DESCRIPTION"] = "查看所有拓展的操作."
                it["SUB-EXPANSION-DESCRIPTION"] = "关于 {expansion_name} 的命令."

                it["INFO"] = YamlConfiguration().also {
                    it["DESCRIPTION"] = "查看该拓展的详细信息."
                    it["TITLE"] = "拓展 &6{expansion_name}&7 的详细信息."
                    it["TEXTS"] = mutableListOf(
                        "  &3&l* &7标识: &a{expansion_sign}",
                        "  &3&l* &7文件后缀: &a{expansion_file_suffix}",
                        "  &3&l* &7绑定的脚本: &f{expansion_bind_scripts}",
                        "",
                        "  &7关于拓展 &6{expansion_name} &7的更多帮助, 请输入:"

                    )
                }
                it["EVALUATE"] = YamlConfiguration().also {
                    it["DESCRIPTION"] = "评估指定内容获得返回值 (':s' 添加在末尾可不显示返回值信息)"
                    it["EVALUATE-RESULT"] = "使用拓展 &6{expansion_name} &7评估的结果: &b{result}"
                }
            }
            it["SCRIPT"] = YamlConfiguration().also {
                it["DESCRIPTION"] = "查看该脚本的信息."
                it["NOT-FOUND-SCRIPT"] = "找不到脚本 &b{script_name}&7! 请检查名称."
                it["SUB-SCRIPT-DESCRIPTION"] = "关于 {script_name} 的命令."

                it["EXECUTE"] = YamlConfiguration().also {
                    it["DESCRIPTION"] = "执行指定内容获取返回值 (':s' 添加在末尾可不显示返回值信息)"

                    it["EXECUTE-RESULT"] = "使用 &6{expansion_name} &7执行脚本 &b{script_name} &7的返回值: {result}"
                }
                it["EVALUATE"] = YamlConfiguration().also {
                    it["DESCRIPTION"] = "评估指定内容获得返回值 (':s' 添加在末尾可不显示返回值信息)"

                    it["EXECUTE-RESULT"] = "使用 &6{expansion_name} &7评估脚本 &b{script_name} &7的返回值: {result}"
                }
                it["RELOAD"] = YamlConfiguration().also {
                    it["DESCRIPTION"] = "重新载入这个脚本."
                    it["RELOADED-SCRIPT"] = "成功重新载入脚本 &b{script_name}&7!"
                }

                it["INFO"] = YamlConfiguration().also {
                    it["DESCRIPTION"] = "查看该脚本的详细信息."
                    it["TITLE"] = "脚本 &b{script_name} &7的详细信息."
                    it["TEXTS"] = mutableListOf(
                        "  &3&l* &7版本: &2{script_version}",
                        "  &3&l* &7作者: &3{script_authors}",
                        "  &3&l* &7描述: &f{script_description}",
                        "  &3&l* &7受保护: &f{script_init_protected}",
                        "  &3&l* &7异步初始化: &f{script_init_use_async}",
                        "  &3&l* &7主函数: &f{script_main}",
                        "  &3&l* &7绑定的拓展: &6{script_bind_expansions}",
                        "",
                        "  &7关于脚本 &6{script_name} &7的更多帮助, 请输入:"

                    )
                }

            }
            it["RELOAD"] = YamlConfiguration().also {
                it["DESCRIPTION"] = "重新载入配置文件或设置."
                it["MODE"] = YamlConfiguration().also {
                    it["ALL"] = "重新载入所有."
                    it["CONFIG"] = "重新载入配置文件."
                    it["SCRIPT"] = "重新载入脚本文件."
                    it["PLUGIN"] = "重新载入插件设置."
                }
                it["LOADED-ALL"] = "载入所有设置成功."
                it["LOADED-CONFIG"] = "载入配置文件成功."
                it["LOADED-SCRIPT"] = "载入脚本文件成功."
                it["LOADED-PLUGIN"] = "载入插件设置成功."

                it["ASYNC-LOADED-ALL"] = "异步载入所有设置成功."
                it["ASYNC-LOADED-CONFIG"] = "异步载入配置文件成功."
                it["ASYNC-LOADED-SCRIPT"] = "异步载入脚本文件成功."
                it["ASYNC-LOADED-PLUGIN"] = "异步载入插件设置成功."
            }
            it["TOOLS"] = YamlConfiguration().also {
                it["DESCRIPTION"] = "一些实用的工具库."

                it["COMMAND"] = YamlConfiguration().also {
                    it["DESCRIPTION"] = "以指定身份执行命令('@CONSOLE' 代表控制台身份)."
                }

                it["BUKKIT"] = YamlConfiguration().also {
                    it["SOUNDS"] = YamlConfiguration().also {
                        it["DESCRIPTION"] = "播放指定声音给玩家."
                        it["NOT-FOUND-SOUND"] = "声音 &8{sound_name} &7没有找到."
                    }
                }
            }
            it["DEBUG"] = YamlConfiguration().also {
                it["DESCRIPTION"] = "用于启动调试或查看一些调试信息."
            }
        }
    }
    it["EXPANSION"] = YamlConfiguration().also {
        it["TYPE-ENGINE"] = YamlConfiguration().also {
            it["EVALUATE-SCRIPT-ERROR"] = "评估脚本 {script_name} 时发送错误, 请检查脚本格式. 原因: \n&8{reason}"
            it["EVALUATE-TEMP-SCRIPT-ERROR"] = "评估临时脚本时发送错误, 请检查脚本格式. 原因: \n&8{reason}"
            it["EXECUTE-SCRIPT-ERROR"] = "脚本 {script_name} 执行函数 {execute_main} 时发生错误, 请检查脚本格式. 原因: \n&8{reason}"
            it["EXECUTE-SCRIPT-FUNCTION-NOT-FOUND-ERROR"] = "脚本 {script_name} 执行函数 {execute_main} 时发生错误, 原因: &8this function not found!"
            it["EXECUTE-TEMP-SCRIPT-ERROR"] = "临时脚本执行函数 {execute_main} 时发生错误, 请检查脚本格式, 原因: \n&8{reason}"
            it["EXECUTE-SCRIPT-FUNCTION-NOT-FOUND-ERROR"] = "临时脚本执行函数 {execute_main} 时发生错误, 原因: &8this function not found!"
        }
        it["ERROR-BY-CAUSE"] = YamlConfiguration().also {
            it["LOAD-ERROR"] = "载入扩展 {file_name} 时发生异常, 原因: \n&8{reason}"
            it["LOAD-DESCRIPTION-FILE-ERROR"] = "载入拓展描述文件 '{file_name}' 时发送错误, 原因: \n&8{reason}"
            it["LOAD-MAIN-CLASS-ERROR"] = "载入扩展 '{file_name}' 的主类 {description_main} 时发生错误, 原因: \n&8{reason}"
            it["LOAD-MAIN-CLASS-MAIN-NOT-DEPEND"] = "载入拓展 {description_main} 描述文件 '{file_name}' 发送错误, 原因: &c主类没有继承 FastScriptExpansion."
            it["CAN-NOT-LOAD-MAIN-CLASS"] = "无法载入扩展 '{file_name}' , 因为它没有找到继承 FastScriptExpansion 的类!"
        }
    }
    it["SCRIPT"] = YamlConfiguration().also {
        it["PROCESS-RESULT"] = YamlConfiguration().also {
            it["SCRIPT-OPTION-FILE-NOT-EXISTS"] = "脚本选项文件存在!"
            it["SCRIPT-TYPE-NOT-SUPPORTED"] = "脚本文件扩展名不受支持!"
            it["SCRIPT-FILE-NAME-CANNOT-SPACES"] = "文件名不能包含空格!"
        }
        it["LISTENERS"] = YamlConfiguration().also {
            it["FAILED-REGISTER"] = "脚本 {script_name} 的一个监听器注册失败, 可能监听类语法存在问题."
            it["FAILED-UNREGISTER"] = "脚本 {script_name} 的一个无法取消注册, 可能是该监听器没有被注册或类的实例不同."
        }

        it["SCRIPT-FAILED-LOAD-BY-PROCESS-RESULT"] = "载入脚本 &3{file_name} &7时发生错误, 原因: &8{reason}"
    }
    it["LOADED-COUNTS-PROCESS-SUCCESS"] = "成功载入 &b{total} &7个 {id}, &7成功数 &a{success}&7.&8({millisecond}ms)"
    it["LOADED-COUNTS-PROCESS-SUCCESS-HAS-FAILED"] = "成功载入 &b{total} &7个 {id}, &7成功数 &a{success}&7, &7失败数 &c{fail}&7.&8({millisecond}ms)"


    it["INVOKE"] = YamlConfiguration().also {
        it["ASYNC-SUCCESS"] = "异步 {exec_type} {exec_name}.&8({millisecond}ms)"
        it["ASYNC-SUCCESS-HAS-DESCRIPTION"] = "异步 {exec_type} {exec_name}, {exec_description}.&8({millisecond}ms)"
        it["ASYNC-FAILED"] = "&cAsync failed to invoke {exec_name}, 原因:\n&8{reason}"

        it["SUCCESS"] = "{exec_type} {exec_name}.&8({millisecond}ms)"
        it["SUCCESS-HAS-DESCRIPTION"] = "{exec_type} {exec_name}, {exec_description}.&8({millisecond}ms)"
        it["FAILED"] = "&c调用 {exec_name} 失败, 原因:\n&8{reason}"
    }

    it["FILE-LISTENER"] = YamlConfiguration().also {
        it["SCRIPT"] = YamlConfiguration().also {
            it["LOADED"] = "监听到文件 &6{file_name} &7改动, 成功重新载入脚本 &b{script_name}&7.&8({millisecond}ms)"
        }
    }

    it["VERSION"] = LanguageManager.version
}
val en_US get() = YamlConfiguration().also {
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
        it["ENABLED"] = "Enabled"
        it["DISABLED"] = "Disabled"
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
                        "  &3&l* &7Protected: &f{script_init_protected}",
                        "  &3&l* &7UseAsync: &f{script_init_use_async}",
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
        it["LISTENERS"] = YamlConfiguration().also {
            it["FAILED-REGISTER"] = "A listener of script {script_name} failed to register, there may be a problem with the syntax of the listener."
            it["FAILED-UNREGISTER"] = "One of the script {script_name} cannot be unregistered, it may be that the listener is not registered or the instance of the class is different."
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
    it["VERSION"] = LanguageManager.version
}
