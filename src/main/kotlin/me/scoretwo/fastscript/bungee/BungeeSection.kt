package me.scoretwo.fastscript.bungee

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.plugin.FastScriptMain
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.plugin.Plugin

class BungeeSection: Plugin(), FastScriptMain {

    override fun onLoad() {
        FastScript.setBootstrap(this)
    }

    override fun onEnable() {
        FastScript.instance?.onReload()
    }

    override fun getPluginClassLoader(): ClassLoader {
        return javaClass.classLoader
    }

    override fun setPlaceholder(player: Any, string: String): String {
        return string
    }

    override fun sendConsoleMessage(message: String) {
        ProxyServer.getInstance().console.sendMessage(ChatColor.translateAlternateColorCodes('&', message))
    }

    override fun sendMessage(sender: Any, string: String) {
        (sender as? CommandSender)?.sendMessage(string)
    }

    override fun onReload() {

    }

}