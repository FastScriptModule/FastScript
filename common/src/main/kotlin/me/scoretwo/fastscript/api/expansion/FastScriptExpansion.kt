package me.scoretwo.fastscript.api.expansion

import me.scoretwo.fastscript.api.script.AbstractScript
import me.scoretwo.fastscript.api.script.FileScript
import me.scoretwo.utils.sender.GlobalSender

abstract class FastScriptExpansion {

    abstract val name: String
    abstract val sign: String

    abstract fun processScripts(scripts: MutableList<FileScript>)

    abstract fun executeScript(sender: GlobalSender, script: FileScript, function: String, args: Array<Any?>)
}