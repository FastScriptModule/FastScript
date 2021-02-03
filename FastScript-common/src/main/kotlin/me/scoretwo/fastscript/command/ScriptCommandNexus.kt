package me.scoretwo.fastscript.command

import me.scoretwo.fastscript.FastScript
import me.scoretwo.utils.command.CommandNexus

class ScriptCommandNexus: CommandNexus(FastScript.instance.plugin, arrayOf("FastScript", "script", "fs")) {

    init {
        subCommands
    }

}