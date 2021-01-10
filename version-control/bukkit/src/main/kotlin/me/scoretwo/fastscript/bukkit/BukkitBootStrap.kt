package me.scoretwo.fastscript.bukkit

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.FormatHeader
import me.scoretwo.fastscript.bukkit.hook.PlaceholderAPIHook
import me.scoretwo.fastscript.sendMessage
import me.scoretwo.utils.bukkit.command.bukkitCommandMap
import me.scoretwo.utils.bukkit.command.toBukkitPlayer
import me.scoretwo.utils.bukkit.plugin.toGlobalPlugin
import me.scoretwo.utils.sender.GlobalPlayer
import org.bstats.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin

class BukkitBootStrap: JavaPlugin() {

    val bukkitPlugin = BukkitPlugin(toGlobalPlugin())

    override fun onLoad() {
        bukkitPlugin.load()
    }

    override fun onEnable() {
        bukkitPlugin.enable()
        bukkitPlugin.reload()
        // 暂无计划
        val metrics = Metrics(this, 9014)
    }

    override fun onDisable() {
        bukkitPlugin.disable()
    }

}