package me.scoretwo.fastscript.api.plugin

import java.io.File

interface FastScriptMain {

    val CONSOLE: Any

    fun getDataFolder(): File

    fun getPluginClassLoader(): ClassLoader

    fun setPlaceholder(player: Any, string: String): String

    fun onReload()

    fun sendMessage(sender: Any, string: String, colorIndex: Boolean)

    fun hasPermission(sender: Any, string: String): Boolean

    fun translateStringColors(string: String): String

}