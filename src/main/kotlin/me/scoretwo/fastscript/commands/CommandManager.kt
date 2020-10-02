package me.scoretwo.fastscript.commands

class CommandManager {

    val commands = mutableListOf<SubCommand>()

    fun initCommands() {
        commands.add(HelpCommand())
    }

    fun execute(sender: Any, args: Array<String>) {
        for (command in commands) {
            TODO()
        }
    }

    fun tabComplete(sender: Any, args: Array<String>): MutableList<String> {
        TODO()
    }

}