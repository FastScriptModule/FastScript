package me.scoretwo.fastscript.bungee

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.plugin.ScriptPlugin
import me.scoretwo.utils.bungee.command.registerBungeeCommands
import me.scoretwo.utils.bungee.command.toBungeePlayer
import me.scoretwo.utils.bungee.command.toBungeeSender
import me.scoretwo.utils.bungee.command.toGlobalSender
import me.scoretwo.utils.bungee.plugin.toBungeePlugin
import me.scoretwo.utils.plugin.GlobalPlugin
import me.scoretwo.utils.sender.GlobalPlayer
import me.scoretwo.utils.sender.GlobalSender
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.Plugin
import net.md_5.bungee.api.plugin.TabExecutor

class BungeePlugin(val plugin: GlobalPlugin): ScriptPlugin(plugin) {

    override fun load() {
        FastScript.setBootstrap(this)
    }

    override fun enable() {
        FastScript.instance.onReload()

        FastScript.instance.commandNexus.registerBungeeCommands()
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