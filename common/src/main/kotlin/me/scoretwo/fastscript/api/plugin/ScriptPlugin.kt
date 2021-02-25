package me.scoretwo.fastscript.api.plugin

import me.scoretwo.fastscript.api.utils.maven.MavenArtifact
import me.scoretwo.utils.plugin.GlobalPlugin
import me.scoretwo.utils.plugin.PluginDescription
import me.scoretwo.utils.plugin.logging.GlobalLogger
import me.scoretwo.utils.sender.GlobalPlayer
import me.scoretwo.utils.sender.GlobalSender
import me.scoretwo.utils.server.GlobalServer
import java.io.File

abstract class ScriptPlugin(plugin: GlobalPlugin): GlobalPlugin {

    abstract fun setPlaceholder(player: GlobalPlayer, string: String): String

    open val libs: MutableList<MavenArtifact> = mutableListOf()

    val scriptKits = mutableMapOf<String, Any?>()

    abstract fun toOriginalSender(sender: GlobalSender): Any?
    abstract fun toOriginalPlayer(player: GlobalPlayer): Any?
    abstract fun toOriginalServer(): Any?

    open fun load() {}
    open fun reload() {}
    open fun enable() {}
    open fun disable() {}

    override val dataFolder: File = plugin.dataFolder
    override val description: PluginDescription = plugin.description
    override val logger: GlobalLogger = plugin.logger
    override val pluginClassLoader: ClassLoader = plugin.pluginClassLoader
    override val server: GlobalServer = plugin.server

}