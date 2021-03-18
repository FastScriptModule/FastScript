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