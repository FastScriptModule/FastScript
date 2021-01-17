package me.scoretwo.fastscript.bukkit

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.api.plugin.ScriptPlugin
import me.scoretwo.fastscript.api.utils.AbstractScriptUtils
import me.scoretwo.fastscript.bukkit.hook.PlaceholderAPIHook
import me.scoretwo.fastscript.sendMessage
import me.scoretwo.utils.bukkit.command.bukkitCommandMap
import me.scoretwo.utils.bukkit.command.toBukkitPlayer
import me.scoretwo.utils.bukkit.command.toBukkitSender
import me.scoretwo.utils.bukkit.command.toGlobalSender
import me.scoretwo.utils.bukkit.plugin.toBukkitPlugin
import me.scoretwo.utils.plugin.GlobalPlugin
import me.scoretwo.utils.sender.GlobalPlayer
import me.scoretwo.utils.sender.GlobalSender
import me.scoretwo.utils.server.GlobalServer
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class BukkitPlugin(val plugin: GlobalPlugin): ScriptPlugin(plugin) {

    override fun load() {
        FastScript.setBootstrap(this)
    }

    override fun enable() {
        bukkitCommandMap.register(description.name, object : Command(description.name, "", "/" + description.name, listOf("script","bukkitScript")) {
            override fun execute(sender: CommandSender, label: String, args: Array<String>): Boolean {
                FastScript.instance.commandNexus.execute(sender.toGlobalSender(), mutableListOf(label), args.toMutableList())
                return true
            }

            override fun tabComplete(sender: CommandSender, alias: String, args: Array<String>): MutableList<String> {
                return FastScript.instance.commandNexus.tabComplete(sender.toGlobalSender(), mutableListOf(label), args.toMutableList()) ?: mutableListOf()
            }
        })
    }

    override val abstractScriptUtils = object : AbstractScriptUtils() {
        override fun toSender(sender: GlobalSender) = sender.toBukkitSender()
        override fun toPlayer(player: GlobalPlayer) = player.toBukkitPlayer()

        override fun toServer() = Bukkit.getServer()

    }

    override fun setPlaceholder(player: GlobalPlayer, string: String): String {
        var text: String = string
        if (PAPIHook != null) {
            text = PlaceholderAPIHook.setPlaceholder(player.toBukkitPlayer(), string)
        }

        return text
    }

    override fun reload() {
        if (PAPIHook == null) {
            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                PAPIHook = PlaceholderAPIHook(plugin.toBukkitPlugin())
                plugin.server.console.sendMessage(FormatHeader.HOOKED, "成功挂钩 §ePlaceholderAPI!")
            }
        }
    }

}

var PAPIHook: PlaceholderAPIHook? = null