package me.scoretwo.fastscript.api.expansion

import me.scoretwo.fastscript.expansion.javascript.JavaScriptExpansion
import me.scoretwo.fastscript.expansion.kotlinscript.KotlinScriptExpansion

class ExpansionManager {

    val expansions = mutableSetOf<FastScriptExpansion>()

    init {
        register(KotlinScriptExpansion())
        register(JavaScriptExpansion())
    }

    fun register(expansion: FastScriptExpansion) {
        expansions.add(expansion)
    }

    fun unregister(expansion: FastScriptExpansion) {
        expansions.remove(expansion)
    }

    fun unregister(name: String) = expansions.forEach {
        if (it.name == name) {
            expansions.remove(it)
            return@forEach
        }
    }

}