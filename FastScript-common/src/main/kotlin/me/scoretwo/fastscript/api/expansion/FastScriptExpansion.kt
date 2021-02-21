package me.scoretwo.fastscript.api.expansion

import me.scoretwo.fastscript.api.script.Script
import me.scoretwo.utils.sender.GlobalSender

abstract class FastScriptExpansion {

    abstract val name: String
    abstract val sign: String
    abstract val fileSuffix: String

    abstract val needEval: Boolean

    abstract fun reload(): FastScriptExpansion

    abstract fun eval(text: String, sender: GlobalSender, vararg args: String): Any?
    abstract fun eval(script: Script, sender: GlobalSender, vararg args: String): Any?
    abstract fun execute(script: Script, sender: GlobalSender, main: String = script.option.main, args: Array<Any?> = arrayOf()): Any?
    abstract fun execute(text: String, sender: GlobalSender, main: String = "main", args: Array<Any?> = arrayOf()): Any?

    // abstract fun eval(script: Script, sender: GlobalSender): Any?

    // abstract fun execute(script: Script, sender: GlobalSender, main: String, args: Array<Any?>): Any?
}