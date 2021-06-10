package me.scoretwo.fastscript.api.plugin

import me.scoretwo.fastscript.api.utils.maven.MavenArtifact
import me.scoretwo.utils.plugin.GlobalPlugin
import me.scoretwo.utils.plugin.PluginDescription
import me.scoretwo.utils.plugin.logging.GlobalLogger
import me.scoretwo.utils.sender.GlobalPlayer
import me.scoretwo.utils.sender.GlobalSender
import me.scoretwo.utils.server.GlobalServer
import java.io.File

abstract class ScriptPlugin(val plugin: GlobalPlugin): GlobalPlugin {

    abstract fun setPlaceholder(player: GlobalPlayer, string: String): String

    open val libs: MutableList<MavenArtifact> = mutableListOf()

    val scriptKits = mutableMapOf<String, Any?>()

    abstract fun toOriginalPlugin(): Any?
    abstract fun toOriginalSender(sender: GlobalSender): Any?
    abstract fun toGlobalSender(any: Any?): GlobalSender
    abstract fun toOriginalPlayer(player: GlobalPlayer): Any?
    abstract fun toGlobalPlayer(any: Any?): GlobalPlayer
    abstract fun toOriginalServer(): Any?

    abstract fun registerListener(any: Any?): Boolean
    abstract fun unregisterListener(any: Any?): Boolean

    open fun load() {}
    open fun reload() {}
    open fun enable() {}
    open fun disable() {}

    override val dataFolder: File = plugin.dataFolder
    override val description: PluginDescription get() = plugin.description
    override val logger: GlobalLogger = plugin.logger
    override val pluginClassLoader: ClassLoader = plugin.pluginClassLoader
    override val server: GlobalServer = plugin.server

}