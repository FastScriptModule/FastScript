package me.scoretwo.fastscript.api.script

import me.scoretwo.fastscript.api.expansion.FastScriptExpansion
import me.scoretwo.utils.sender.GlobalSender

abstract class ScriptProcessor(val script: Script, val expansion: FastScriptExpansion) {

    abstract fun eval(sender: GlobalSender): Any?
    abstract fun execute(sender: GlobalSender, main: String = script.options.main, args: Array<Any?> = arrayOf()): Any?

}