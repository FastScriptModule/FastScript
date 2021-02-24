package me.scoretwo.fastscript.command.commands

import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.command.SimpleCommand
import me.scoretwo.fastscript.languages
import me.scoretwo.fastscript.sendMessage
import me.scoretwo.fastscript.setPlaceholder
import me.scoretwo.utils.sender.GlobalSender

/**
 * @author Score2
 * @date 2021/2/24 10:57
 *
 * @project FastScript
 */
class MigrateCommand: SimpleCommand(arrayOf("migrate")) {

    override var description = languages["COMMAND-NEXUS.COMMANDS.MIGRATE.DESCRIPTION"]

    override fun execute(sender: GlobalSender, parents: Array<String>, args: Array<String>): Boolean {
        if (args.isEmpty()) {
            return false
        }

        when (args[0]) {
            "PlaceholderAPI:JavaScript" -> {
                sender.sendMessage(FormatHeader.ERROR, "Not currently supported.")
            }
            else -> {
                sender.sendMessage(FormatHeader.ERROR, languages["COMMAND-NEXUS.COMMANDS.MIGRATE.UNKNOWN-ACTION"].setPlaceholder("action_name" to args[0]))
            }
        }

        return true
    }

    override fun tabComplete(sender: GlobalSender, parents: Array<String>, args: Array<String>): MutableList<String>? {
        if (args.size < 2) {
            return mutableListOf("PlaceholderAPI:JavaScript")
        }
        return null
    }

}