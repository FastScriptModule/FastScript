package me.scoretwo.fastscript.bungee

import me.scoretwo.utils.bungee.plugin.toGlobalPlugin
import net.md_5.bungee.api.plugin.Plugin

class BungeeBootStrap: Plugin() {

    val bungeePlugin = BungeePlugin(toGlobalPlugin())

    override fun onLoad() {
        bungeePlugin.load()
    }

    override fun onEnable() {
        bungeePlugin.enable()
    }

    override fun onDisable() {
        bungeePlugin.disable()
    }

}