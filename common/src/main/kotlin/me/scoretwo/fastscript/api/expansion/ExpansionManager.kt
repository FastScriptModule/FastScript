package me.scoretwo.fastscript.api.expansion

class ExpansionManager {

    val expansions = mutableSetOf<FastScriptExpansion>()

    init {

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