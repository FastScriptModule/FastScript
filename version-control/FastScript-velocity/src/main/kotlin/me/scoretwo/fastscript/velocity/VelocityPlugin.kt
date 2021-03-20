package me.scoretwo.fastscript.velocity

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.plugin.ScriptPlugin
import me.scoretwo.fastscript.api.utils.ExecType
import me.scoretwo.fastscript.api.utils.maven.MavenArtifact
import me.scoretwo.utils.plugin.GlobalPlugin
import me.scoretwo.utils.sender.GlobalPlayer
import me.scoretwo.utils.sender.GlobalSender
import me.scoretwo.utils.velocity.command.registerVelocityCommands
import me.scoretwo.utils.velocity.command.toVelocityPlayer
import me.scoretwo.utils.velocity.command.toVelocitySender
import me.scoretwo.utils.velocity.plugin.toVelocityPlugin
import me.scoretwo.utils.velocity.server.proxyServer

class VelocityPlugin(plugin: GlobalPlugin): ScriptPlugin(plugin) {

    override val libs = mutableListOf(MavenArtifact("net.md-5:bungeecord-api:1.16-R0.4", "https://maven.aliyun.com/repository/central"))

    override fun load() {
        FastScript.setBootstrap(this)
    }

    override fun enable() {
        FastScript.instance.reload("script", "plugin")
    }

    override fun setPlaceholder(player: GlobalPlayer, string: String): String {
        return string
    }

    override fun toOriginalPlugin() = toVelocityPlugin()

    override fun toOriginalSender(sender: GlobalSender) = sender.toVelocitySender()

    override fun toOriginalPlayer(player: GlobalPlayer) = player.toVelocityPlayer()

    override fun toOriginalServer() = proxyServer

    override fun registerListener(any: Any?): Boolean = try {
        proxyServer.eventManager.register(toVelocityPlugin(), any ?: false)
        true
    } catch (t: Throwable) {
        false
    }

    override fun unregisterListener(any: Any?): Boolean = try {
        proxyServer.eventManager.unregisterListener(toVelocityPlugin(), any ?: false)
        true
    } catch (t: Throwable) {
        false
    }

    override fun reload() {

    }
}