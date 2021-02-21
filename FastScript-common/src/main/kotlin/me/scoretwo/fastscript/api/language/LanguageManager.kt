package me.scoretwo.fastscript.api.language

import net.md_5.bungee.api.ChatColor

class LanguageManager {
    val version = "1"
    val defaultLanguage = Language()
    val languages = mutableMapOf<String, Language>().also {
        it["en_US"] = defaultLanguage
    }

    var current = defaultLanguage

    operator fun set(node: String, any: Any?) = current.set(node, any)

    operator fun get(node: String): String = ChatColor.translateAlternateColorCodes('&', current[node])

    fun getList(node: String) = current.getList(node).also { it.toMutableList().forEachIndexed { i, s -> it[i] = ChatColor.translateAlternateColorCodes('&', s) } }

}