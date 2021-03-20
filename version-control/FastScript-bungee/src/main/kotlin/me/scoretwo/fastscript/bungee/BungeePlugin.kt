package me.scoretwo.fastscript.bungee

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.plugin.ScriptPlugin
import me.scoretwo.fastscript.api.utils.ExecType
import me.scoretwo.utils.bungee.command.registerBungeeCommands
import me.scoretwo.utils.bungee.command.toBungeePlayer
import me.scoretwo.utils.bungee.command.toBungeeSender
import me.scoretwo.utils.bungee.plugin.toBungeePlugin
import me.scoretwo.utils.plugin.GlobalPlugin
import me.scoretwo.utils.sender.GlobalPlayer
import me.scoretwo.utils.sender.GlobalSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.plugin.Listener

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

    override fun toOriginalPlugin() = toBungeePlugin()

    override fun toOriginalSender(sender: GlobalSender) = sender.toBungeeSender()

    override fun toOriginalPlayer(player: GlobalPlayer) = player.toBungeePlayer()

    override fun toOriginalServer(): Any? = ProxyServer.getInstance()

    override fun registerListener(any: Any): Boolean {
        if (any !is Listener) {
            return false
        }

        ProxyServer.getInstance().pluginManager.registerListener(toBungeePlugin(), any)
        return true
    }

    override fun unregisterListener(any: Any): Boolean {
        if (any !is Listener) {
            return false
        }

        ProxyServer.getInstance().pluginManager.unregisterListener(any)
        return true
    }

    override fun reload() {

    }

}