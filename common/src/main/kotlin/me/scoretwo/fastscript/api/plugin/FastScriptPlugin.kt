package me.scoretwo.fastscript.api.plugin

import me.scoretwo.fastscript.api.plugin.logging.ScriptLogger
import me.scoretwo.utils.command.GlobalSender
import java.io.File

interface FastScriptPlugin {

    val console: GlobalSender
    val dataFolder: File
    val pluginClassLoader: ClassLoader
    val scriptLogger: ScriptLogger

    fun setPlaceholder(player: Any, string: String): String
    fun translateStringColors(string: String): String

    fun onReload()

}