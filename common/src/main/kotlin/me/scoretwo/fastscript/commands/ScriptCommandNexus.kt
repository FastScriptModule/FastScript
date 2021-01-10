package me.scoretwo.fastscript.commands

import me.scoretwo.fastscript.FastScript
import me.scoretwo.utils.command.CommandNexus

class ScriptCommandNexus: CommandNexus(FastScript.instance.plugin, arrayOf("FastScript", "script")) {

    init {
        subCommands
    }

}