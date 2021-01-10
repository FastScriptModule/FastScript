package me.scoretwo.fastscript.bungee

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.plugin.ScriptPlugin
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.Plugin
import net.md_5.bungee.api.plugin.TabExecutor

class BungeePlugin: Plugin(), ScriptPlugin {

    override fun onLoad() {
    }

    override fun onEnable() {
        FastScript.setBootstrap(this)
        FastScript.instance.onReload()

        ProxyServer.getInstance().pluginManager.registerCommand(this, object : Command("FastScript", null, "script"), TabExecutor {
            override fun execute(sender: CommandSender, args: Array<String>) {
                FastScript.instance.commandManager.execute(sender, args)
            }

            override fun onTabComplete(sender: CommandSender, args: Array<String>): MutableIterable<String> {
                return FastScript.instance.commandManager.tabComplete(sender, args)
            }
        })
    }

    override val console: Any = ProxyServer.getInstance().console

    override fun getPluginClassLoader(): ClassLoader {
        return javaClass.classLoader
    }

    override fun setPlaceholder(player: Any, string: String): String {
        return string
    }

    override fun translateStringColors(string: String): String {
        return ChatColor.translateAlternateColorCodes('&', string)
    }

    override fun onReload() {

    }

}