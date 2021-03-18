package me.scoretwo.fastscript.api.language

import me.scoretwo.fastscript.plugin
import net.md_5.bungee.api.ChatColor
import java.io.File

class LanguageManager {
    val version = Companion.version
    val defaultLanguage = Language(version).reload()
    val languages = mutableMapOf<String, Language>().also {
        File(plugin.dataFolder, "language/zh_CN.yml").also {
            if (!it.exists()) zh_CN.save(it)
        }

        it["en_US"] = defaultLanguage
        it["zh_CN"] = Language(version, "zh_CN")
    }

    init {
        File(plugin.dataFolder, "language").listFiles()?.forEach {
            val name = it.name.substringBeforeLast(".")
            languages[name] = Language(version, name)
        }
    }

    var current = defaultLanguage

    operator fun set(node: String, any: Any?) = current.set(node, any)

    operator fun get(node: String): String = ChatColor.translateAlternateColorCodes('&', current[node])

    fun getList(node: String) = current.getList(node).also { it.toMutableList().forEachIndexed { i, s -> it[i] = ChatColor.translateAlternateColorCodes('&', s) } }

    companion object {
        const val version = "2"
    }

}