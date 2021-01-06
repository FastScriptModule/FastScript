package me.scoretwo.fastscript.commands

abstract class SubCommand(val name: String, val sendLimit: SendLimit = SendLimit.PERMISSION, val alias: Array<String> = arrayOf()) {

    abstract fun execute(sender: Any, args: Array<String>)

    open fun tabComplete(sender: Any, args: Array<String>): MutableList<String> {
        return mutableListOf()
    }

    companion object {
        enum class SendLimit { PLAYER, console, ALL, PERMISSION }


    }

}