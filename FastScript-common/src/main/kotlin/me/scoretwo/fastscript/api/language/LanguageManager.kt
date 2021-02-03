package me.scoretwo.fastscript.api.language

class LanguageManager {

    val languages = mutableMapOf<String, Language>().also {
        it["en_US"] = Language()
    }

    var current = languages["en_US"]!!


    operator fun set(node: String, any: Any?) = current.set(node, any)

    operator fun get(node: String) = current[node]


    fun getList(node: String) = current.getList(node)

}