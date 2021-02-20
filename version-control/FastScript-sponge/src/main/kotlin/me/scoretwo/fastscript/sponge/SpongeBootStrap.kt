package me.scoretwo.fastscript.sponge

import com.google.inject.Inject
import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.plugin.ScriptPluginState
import me.scoretwo.utils.sponge.plugin.toGlobalPlugin
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.game.state.GameInitializationEvent
import org.spongepowered.api.event.game.state.GamePreInitializationEvent
import org.spongepowered.api.plugin.Dependency
import org.spongepowered.api.plugin.Plugin
import org.spongepowered.api.event.game.state.GameStoppingEvent
import org.spongepowered.api.event.message.MessageEvent
import org.spongepowered.api.plugin.PluginContainer

@Plugin(
    id = "fastscript",
    name = "FastScript",
    authors = ["Score2"],
    dependencies = [Dependency(id = "placeholderapi", optional = true)]
)
class SpongeBootStrap @Inject constructor(val pluginContainer: PluginContainer) {

    val spongePlugin = SpongePlugin(pluginContainer.toGlobalPlugin())

    @Listener
    fun onLoad(e: GamePreInitializationEvent) {
        spongePlugin.load()
    }

    @Listener
    fun onEnable(e: GameInitializationEvent) {
        spongePlugin.enable()
        FastScript.stats = ScriptPluginState.RUNNING
    }

    @Listener
    fun onDisable(e: GameStoppingEvent) {
        spongePlugin.disable()
        FastScript.stats = ScriptPluginState.DISABLE
    }

}