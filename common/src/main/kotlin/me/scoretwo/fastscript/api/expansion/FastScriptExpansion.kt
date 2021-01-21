package me.scoretwo.fastscript.api.expansion

import me.scoretwo.fastscript.api.script.Script
import me.scoretwo.fastscript.scripts
import me.scoretwo.utils.sender.GlobalSender

abstract class FastScriptExpansion {

    abstract val name: String
    abstract val sign: String
    abstract val fileSuffix: String

    abstract fun convertScriptProcessor(script: Script): Boolean

    // abstract fun eval(script: Script, sender: GlobalSender): Any?

    // abstract fun execute(script: Script, sender: GlobalSender, main: String, args: Array<Any?>): Any?

    fun getHookedScripts() = mutableListOf<Script>().also { list ->
        scripts.forEach {
            if (it.scriptProcessor.containsKey(sign)) list.add(it)
        }
    }
}