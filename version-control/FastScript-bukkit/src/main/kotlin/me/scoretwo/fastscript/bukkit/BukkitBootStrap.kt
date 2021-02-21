package me.scoretwo.fastscript.bukkit

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.plugin.ScriptPluginState
import me.scoretwo.fastscript.plugin
import me.scoretwo.utils.bukkit.plugin.toGlobalPlugin
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.Bukkit
import java.lang.Exception
import java.util.concurrent.Callable

class BukkitBootStrap: JavaPlugin() {

    val bukkitPlugin = BukkitPlugin(toGlobalPlugin())

    override fun onLoad() {
        bukkitPlugin.load()
    }

    override fun onEnable() {
        bukkitPlugin.enable()
        bukkitPlugin.reload()
        org.bstats.bukkit.Metrics(this, 9014).also { metrics ->
            metrics.addCustomChart(org.bstats.bukkit.Metrics.SingleLineChart("Scripts") {
                FastScript.instance.scriptManager.scripts.size
            })
            metrics.addCustomChart(org.bstats.bukkit.Metrics.SingleLineChart("Evaluate and Execute Counts") {
                val count = FastScript.instance.scriptManager.operationCount
                FastScript.instance.scriptManager.operationCount = 0
                count
            })
            metrics.addCustomChart(org.bstats.bukkit.Metrics.AdvancedPie("Operation Modes") {
                mutableMapOf<String, Int>().also {
                    it["Evaluate"] = FastScript.instance.scriptManager.evaluateCount
                    FastScript.instance.scriptManager.evaluateCount = 0
                    it["Execute"] = FastScript.instance.scriptManager.executeCount
                    FastScript.instance.scriptManager.executeCount = 0
                }
            })
            metrics.addCustomChart(org.bstats.bukkit.Metrics.SimplePie("Server Brand") {
                plugin.server.brand.name
            })
        }
        com.iroselle.cstats.bukkit.Metrics(this).also {  metrics ->
            metrics.addCustomChart(com.iroselle.cstats.bukkit.Metrics.SingleLineChart("Scripts") {
                FastScript.instance.scriptManager.scripts.size
            })
            metrics.addCustomChart(com.iroselle.cstats.bukkit.Metrics.SingleLineChart("Evaluate and Execute Counts") {
                val count = FastScript.instance.scriptManager.operationCount
                FastScript.instance.scriptManager.operationCount = 0
                count
            })
            metrics.addCustomChart(com.iroselle.cstats.bukkit.Metrics.AdvancedPie("Operation Modes") {
                mutableMapOf<String, Int>().also {
                    it["Evaluate"] = FastScript.instance.scriptManager.evaluateCount
                    FastScript.instance.scriptManager.evaluateCount = 0
                    it["Execute"] = FastScript.instance.scriptManager.executeCount
                    FastScript.instance.scriptManager.executeCount = 0
                }
            })
            metrics.addCustomChart(com.iroselle.cstats.bukkit.Metrics.SimplePie("Server Brand") {
                plugin.server.brand.name
            })
        }
        FastScript.stats = ScriptPluginState.RUNNING
    }

    override fun onDisable() {
        bukkitPlugin.disable()
        FastScript.stats = ScriptPluginState.DISABLE
    }

}