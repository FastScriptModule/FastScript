package me.scoretwo.fastscript.bukkit

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.api.plugin.ScriptPlugin
import me.scoretwo.fastscript.api.plugin.ScriptPluginState
import me.scoretwo.fastscript.api.utils.ExecType
import me.scoretwo.fastscript.bukkit.hook.PlaceholderAPIHook
import me.scoretwo.fastscript.sendMessage
import me.scoretwo.utils.bukkit.command.*
import me.scoretwo.utils.bukkit.plugin.toBukkitPlugin
import me.scoretwo.utils.plugin.GlobalPlugin
import me.scoretwo.utils.sender.GlobalPlayer
import me.scoretwo.utils.sender.GlobalSender
import org.bukkit.Bukkit

class BukkitPlugin(val plugin: GlobalPlugin): ScriptPlugin(plugin) {

    override fun load() {
        FastScript.setBootstrap(this)
    }

    override fun enable() {
        FastScript.instance.reload("script", "plugin")

        FastScript.instance.commandNexus.registerBukkitCommands()
    }

    override fun setPlaceholder(player: GlobalPlayer, string: String): String {
        var text: String = string
        if (PAPIHook != null) {
            text = PlaceholderAPIHook.setPlaceholder(player.toBukkitPlayer(), string)
        }

        return text
    }

    override fun toOriginalSender(sender: GlobalSender) = sender.toBukkitSender()

    override fun toOriginalPlayer(player: GlobalPlayer) = player.toBukkitPlayer()

    override fun toOriginalServer() = Bukkit.getServer()

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