package me.scoretwo.fastscript.command.commands

import me.scoretwo.fastscript.plugin
import me.scoretwo.utils.command.SubCommand
import me.scoretwo.utils.sender.GlobalSender

class ScriptCommand: SubCommand(plugin, arrayOf("script")) {



    override fun executed(sender: GlobalSender, parents: MutableList<String>, args: MutableList<String>): Boolean {
        if (args.isEmpty()) {
            // list script
        }



        return true
    }


}