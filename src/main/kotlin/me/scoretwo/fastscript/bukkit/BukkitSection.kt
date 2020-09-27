package me.scoretwo.fastscript.bukkit

import me.scoretwo.fastscript.api.plugin.FastScriptMain
import org.bukkit.plugin.java.JavaPlugin

class BukkitSection: JavaPlugin(), FastScriptMain {

    override fun onEnable() {

    }

    override fun getPluginClassLoader(): ClassLoader {
        return super.getClassLoader()
    }
}