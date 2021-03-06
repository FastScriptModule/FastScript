package me.scoretwo.fastscript.velocity

import com.google.inject.Inject
import com.velocitypowered.api.event.PostOrder
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.plugin.ScriptPluginState
import me.scoretwo.utils.velocity.plugin.toGlobalPlugin
import java.nio.file.Path
import java.util.logging.Logger

@Plugin(
    id = "fastscript",
    name = "FastScript",
    authors = ["Score2"]
)
class VelocityBootStrap {
    @Inject
    lateinit var proxy: ProxyServer

    lateinit var velocityPlugin: VelocityPlugin

    @Subscribe(order = PostOrder.NORMAL)
    fun onEnable(e: ProxyInitializeEvent) {
        val plugin = proxy.pluginManager.fromInstance(this).get()
        velocityPlugin = VelocityPlugin(plugin.toGlobalPlugin(proxy))
        velocityPlugin.load()
        velocityPlugin.enable()
        velocityPlugin.reload()
        FastScript.stats = ScriptPluginState.RUNNING
    }

    @Subscribe(order = PostOrder.NORMAL)
    fun onDisable(e: ProxyShutdownEvent) {
        velocityPlugin.disable()
        FastScript.stats = ScriptPluginState.DISABLE
    }
}