package me.scoretwo.fastscript.api.language

class LanguageManager {

    val defaultLanguage = Language()
    val languages = mutableMapOf<String, Language>().also {
        it["en_US"] = defaultLanguage
    }

    var current = defaultLanguage

    operator fun set(node: String, any: Any?) = current.set(node, any)

    operator fun get(node: String) = current[node]


    fun getList(node: String) = current.getList(node)

}