package me.scoretwo.fastscript.velocity

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.plugin.ScriptPlugin
import me.scoretwo.fastscript.api.utils.ExecType
import me.scoretwo.utils.plugin.GlobalPlugin
import me.scoretwo.utils.sender.GlobalPlayer
import me.scoretwo.utils.sender.GlobalSender
import me.scoretwo.utils.velocity.command.registerVelocityCommands
import me.scoretwo.utils.velocity.command.toVelocityPlayer
import me.scoretwo.utils.velocity.command.toVelocitySender
import me.scoretwo.utils.velocity.server.proxyServer

class VelocityPlugin(val plugin: GlobalPlugin): ScriptPlugin(plugin) {

    override fun load() {
        FastScript.setBootstrap(this)
    }

    override fun enable() {
        FastScript.instance.reload("script", "plugin")

        FastScript.instance.commandNexus.registerVelocityCommands()
        FastScript.stats = ExecType.Loaded
    }

    override fun setPlaceholder(player: GlobalPlayer, string: String): String {
        return string
    }

    override fun toOriginalSender(sender: GlobalSender) = sender.toVelocitySender()

    override fun toOriginalPlayer(player: GlobalPlayer) = player.toVelocityPlayer()

    override fun toOriginalServer() = proxyServer

    override fun reload() {

    }
}