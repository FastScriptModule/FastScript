package me.scoretwo.fastscript.bukkit

import me.scoretwo.utils.bukkit.plugin.toGlobalPlugin
import org.bstats.bukkit.Metrics
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