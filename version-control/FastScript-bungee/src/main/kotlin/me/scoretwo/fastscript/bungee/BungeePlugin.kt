package me.scoretwo.fastscript.bungee

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.plugin.ScriptPlugin
import me.scoretwo.fastscript.api.utils.ExecType
import me.scoretwo.utils.bungee.command.registerBungeeCommands
import me.scoretwo.utils.bungee.command.toBungeePlayer
import me.scoretwo.utils.bungee.command.toBungeeSender
import me.scoretwo.utils.plugin.GlobalPlugin
import me.scoretwo.utils.sender.GlobalPlayer
import me.scoretwo.utils.sender.GlobalSender
import net.md_5.bungee.api.ProxyServer

class BungeePlugin(plugin: GlobalPlugin): ScriptPlugin(plugin) {

    override fun load() {
        FastScript.setBootstrap(this)
    }

    override fun enable() {
        FastScript.instance.reload("script", "plugin")
    }

    override fun setPlaceholder(player: GlobalPlayer, string: String): String {
        return string
    }

    override fun toOriginalSender(sender: GlobalSender) = sender.toBungeeSender()

    override fun toOriginalPlayer(player: GlobalPlayer) = player.toBungeePlayer()

    override fun toOriginalServer(): Any? = ProxyServer.getInstance()

    override fun reload() {

    }

}