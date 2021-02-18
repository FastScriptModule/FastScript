package me.scoretwo.fastscript.command.commands

import me.scoretwo.fastscript.sendMessage
import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.command.SimpleCommand
import me.scoretwo.utils.sender.GlobalSender

/**
 * @author Score2
 * @date 2021/2/6 14:50
 *
 * @project FastScript
 */
class ReloadCommand: SimpleCommand(arrayOf("reload")) {

    override var description = "重新载入配置文件或设置."

    override var moreArgs: Array<String>? = arrayOf()

    override var customCommands: MutableMap<String, Pair<Array<String>?, String>> = mutableMapOf(
        "all" to Pair(null, "重新载入所有设置."),
        "config" to Pair(null, "重新载入配置文件."),
        "script" to Pair(null, "重新载入脚本设置."),
        "plugin" to Pair(null, "重新载入插件设置."),
    )

    override fun execute(sender: GlobalSender, parents: Array<String>, args: Array<String>): Boolean {
        var mode = "all"
        if (args.isNotEmpty()) {
            mode = args[0].toLowerCase()
        }

        if (mode == "all") {
            FastScript.instance.reloadAll()
            sender.sendMessage(FormatHeader.INFO, "成功载入所有设置.")
            return true
        }
        FastScript.instance.reload(mode)
        when (mode) {
            "config" -> sender.sendMessage(FormatHeader.INFO, "成功载入配置文件.")
            "script" -> sender.sendMessage(FormatHeader.INFO, "成功载入脚本设置.")
            "plugin" -> sender.sendMessage(FormatHeader.INFO, "成功载入插件设置.")
        }

        return true
    }

    override fun tabComplete(sender: GlobalSender, parents: Array<String>, args: Array<String>) = mutableListOf("config", "script", "plugin", "all")


}