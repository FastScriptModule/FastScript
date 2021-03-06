package me.scoretwo.fastscript.command.commands

import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.command.SimpleCommand
import me.scoretwo.fastscript.languages
import me.scoretwo.fastscript.sendMessage
import me.scoretwo.fastscript.setPlaceholder
import me.scoretwo.utils.command.SubCommand
import me.scoretwo.utils.command.executor.Executors
import me.scoretwo.utils.sender.GlobalSender

/**
 * @author Score2
 * @date 2021/2/22 17:33
 *
 * @project FastScript
 */
class ToolsCommand: SimpleCommand(arrayOf("tools", "tool", "utils", "util")) {

    override var description = languages["COMMAND-NEXUS.COMMANDS.TOOLS.DESCRIPTION"]

    override var subCommands = mutableListOf(
        nextBuilder().alias("command", "cmd", "execute", "run")
            .description(languages["COMMAND-NEXUS.COMMANDS.TOOLS.COMMAND.DESCRIPTION"])
            .executor(object : Executors {
                override fun execute(sender: GlobalSender, parents: Array<String>, args: Array<String>): Boolean {
                    if (args.isEmpty()) {
                        return false
                    }
                    val command = args.sliceArray(1 until args.size).joinToString(" ")
                    if (args[0].toLowerCase() == "@all") {
                        plugin.server.getOnlinePlayers().forEach { plugin.server.dispatchCommand(it, command) }
                        return true
                    }

                    val target = if (args[0].toLowerCase() == "@console") plugin.server.console else plugin.server.getPlayer(args[0]).let {
                        if (it.isPresent) {
                            it.get()
                        } else {
                            sender.sendMessage(FormatHeader.ERROR, languages["COMMAND-NEXUS.HELPER.PLAYER-IS-OFFLINE"].setPlaceholder("player_name" to args[0]))
                            return true
                        }
                    }
                    plugin.server.dispatchCommand(target, command)
                    return true
                }

                override fun tabComplete(sender: GlobalSender, parents: Array<String>, args: Array<String>) =
                    mutableListOf("@CONSOLE", "@ALL").also { list -> plugin.server.getOnlinePlayers().forEach { list.add(it.name) } }
            })
            .build()
    )

}