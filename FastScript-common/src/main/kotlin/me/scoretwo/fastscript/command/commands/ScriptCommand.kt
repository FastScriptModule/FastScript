package me.scoretwo.fastscript.command.commands

import me.scoretwo.fastscript.command.SimpleCommand
import me.scoretwo.fastscript.plugin
import me.scoretwo.utils.command.SubCommand
import me.scoretwo.utils.sender.GlobalSender

class ScriptCommand: SimpleCommand(arrayOf("script")) {

    override fun execute(sender: GlobalSender, parents: Array<String>, args: Array<String>): Boolean {
        if (args.isEmpty()) {
            // list script
            return true
        }

        if (args[0] == "") {

        }


        return true
    }


}