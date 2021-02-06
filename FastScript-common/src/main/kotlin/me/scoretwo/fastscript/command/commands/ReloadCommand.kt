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
        sender.sendMessage(FormatHeader.INFO, "成功载入部分设置.")
        return true
    }

    override fun tabComplete(sender: GlobalSender, parents: Array<String>, args: Array<String>) = mutableListOf("config", "script", "plugin", "all")


}