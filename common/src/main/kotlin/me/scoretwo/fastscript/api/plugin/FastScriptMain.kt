package me.scoretwo.fastscript.api.plugin

import java.io.File

interface FastScriptMain {

    val console: Any

    fun getDataFolder(): File

    fun getPluginClassLoader(): ClassLoader

    fun setPlaceholder(player: Any, string: String): String

    fun onReload()

    fun translateStringColors(string: String): String

}