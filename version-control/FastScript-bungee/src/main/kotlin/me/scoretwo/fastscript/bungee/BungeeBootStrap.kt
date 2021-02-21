package me.scoretwo.fastscript.bungee

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.plugin.ScriptPluginState
import me.scoretwo.fastscript.plugin
import me.scoretwo.utils.bungee.plugin.toGlobalPlugin
import net.md_5.bungee.api.plugin.Plugin

class BungeeBootStrap: Plugin() {

    val bungeePlugin = BungeePlugin(toGlobalPlugin())

    override fun onLoad() {
        bungeePlugin.load()
    }

    override fun onEnable() {
        bungeePlugin.enable()
        bungeePlugin.reload()
        org.bstats.bungeecord.Metrics(this, 9014).also { metrics ->
            metrics.addCustomChart(org.bstats.bungeecord.Metrics.SingleLineChart("scripts") {
                FastScript.instance.scriptManager.scripts.size
            })
            metrics.addCustomChart(org.bstats.bungeecord.Metrics.SingleLineChart("evaluate_and_execute_counts") {
                val count = FastScript.instance.scriptManager.operationCount
                FastScript.instance.scriptManager.operationCount = 0
                count
            })
            metrics.addCustomChart(org.bstats.bungeecord.Metrics.AdvancedPie("operation_modes") {
                mutableMapOf<String, Int>().also {
                    it["evaluate"] = FastScript.instance.scriptManager.evaluateCount
                    FastScript.instance.scriptManager.evaluateCount = 0
                    it["execute"] = FastScript.instance.scriptManager.executeCount
                    FastScript.instance.scriptManager.executeCount = 0
                }
            })
            metrics.addCustomChart(org.bstats.bungeecord.Metrics.SimplePie("server_brand") {
                plugin.server.brand.name
            })
        }
        com.iroselle.cstats.bungeecord.Metrics(this).also {  metrics ->
            metrics.addCustomChart(com.iroselle.cstats.bungeecord.Metrics.SingleLineChart("scripts") {
                FastScript.instance.scriptManager.scripts.size
            })
            metrics.addCustomChart(com.iroselle.cstats.bungeecord.Metrics.SingleLineChart("evaluate_and_execute_counts") {
                val count = FastScript.instance.scriptManager.operationCount
                FastScript.instance.scriptManager.operationCount = 0
                count
            })
            metrics.addCustomChart(com.iroselle.cstats.bungeecord.Metrics.AdvancedPie("operation_modes") {
                mutableMapOf<String, Int>().also {
                    it["evaluate"] = FastScript.instance.scriptManager.evaluateCount
                    FastScript.instance.scriptManager.evaluateCount = 0
                    it["execute"] = FastScript.instance.scriptManager.executeCount
                    FastScript.instance.scriptManager.executeCount = 0
                }
            })
            metrics.addCustomChart(com.iroselle.cstats.bungeecord.Metrics.SimplePie("server_brand") {
                plugin.server.brand.name
            })
        }
        FastScript.stats = ScriptPluginState.RUNNING
    }

    override fun onDisable() {
        bungeePlugin.disable()
        FastScript.stats = ScriptPluginState.DISABLE
    }

}