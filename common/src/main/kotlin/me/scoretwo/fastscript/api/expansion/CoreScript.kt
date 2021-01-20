package me.scoretwo.fastscript.api.expansion

import me.scoretwo.utils.sender.GlobalSender

interface CoreScript {
    fun directEval(sender: GlobalSender): Any?
    fun execute(sender: GlobalSender, main: String, args: Array<Any?>)
}