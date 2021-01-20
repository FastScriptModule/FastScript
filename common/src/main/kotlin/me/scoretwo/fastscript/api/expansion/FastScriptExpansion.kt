package me.scoretwo.fastscript.api.expansion

import me.scoretwo.fastscript.api.script.AbstractScript
import me.scoretwo.fastscript.api.script.FileScript
import me.scoretwo.fastscript.scripts
import me.scoretwo.utils.sender.GlobalSender

abstract class FastScriptExpansion {

    abstract val name: String
    abstract val sign: String

    fun getHookedScripts() = mutableListOf<FileScript>().also { list ->
        scripts.forEach {
            if (it.hookExpansions.contains(this)) list.add(it)
        }
    }
}