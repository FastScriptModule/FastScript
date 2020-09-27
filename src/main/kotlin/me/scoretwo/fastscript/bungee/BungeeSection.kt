package me.scoretwo.fastscript.bungee

import me.scoretwo.fastscript.api.plugin.FastScriptMain
import net.md_5.bungee.api.plugin.Plugin

class BungeeSection: Plugin(), FastScriptMain {

    override fun onEnable() {

    }

    override fun getPluginClassLoader(): ClassLoader {
        return javaClass.classLoader
    }

}