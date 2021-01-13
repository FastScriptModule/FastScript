package me.scoretwo.fastscript.sponge

import me.rojo8399.placeholderapi.PlaceholderService
import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.api.plugin.ScriptPlugin
import me.scoretwo.fastscript.sponge.hook.PlaceholderAPIHook
import net.md_5.bungee.api.ChatColor
import org.spongepowered.api.Sponge
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.spec.CommandSpec
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.game.state.GameAboutToStartServerEvent
import org.spongepowered.api.event.game.state.GameStoppingServerEvent
import org.spongepowered.api.plugin.Dependency
import org.spongepowered.api.plugin.Plugin
import org.spongepowered.api.text.Text
import java.io.File


@Plugin(
    id = "fastscript",
    name = "FastScript",
    authors = ["Score2"],
    description = "%%description%%",
    dependencies = [Dependency(id = "placeholderapi", optional = true)],
    version = "%%version%%"
)
class SpongePlugin: ScriptPlugin {

    @Listener
    fun onStart(e: GameAboutToStartServerEvent) {
        PlaceholderAPIHook.placeholderService = Sponge.getServiceManager().provideUnchecked(PlaceholderService::class.java)

        FastScript.setBootstrap(this)
        FastScript.instance.onReload()

        val myCommandSpec = CommandSpec.builder()
            .description(Text.of("Hello World Command"))
            .permission("")
            .executor { src, args ->
                FastScript.instance.commandManager.execute(src, args.getAll<String>("").toTypedArray())
                CommandResult.success()
            }
            .build()

        Sponge.getCommandManager().register(this, myCommandSpec, "FastScript", "script")
    }

    @Listener
    fun onStart(e: GameStoppingServerEvent) {

    }

    override val console: Any = Sponge.getServer().console

    override fun getDataFolder(): File {
        return Sponge.getConfigManager().getPluginConfig(this).directory.toFile()
    }

    override fun getPluginClassLoader(): ClassLoader {
        return javaClass.classLoader
    }

    override fun setPlaceholder(player: Any, string: String): String {
        return PlaceholderAPIHook.placeholderService.replaceSourcePlaceholders(string, player).toPlain()
    }

    override fun onReload() {
        if (PAPIHook == null) {
            if (Sponge.getPluginManager().isLoaded("placeholderapi")) {
                PAPIHook = PlaceholderAPIHook(this)
                plugin.server.console.sendMessage(FormatHeader.HOOKED, "成功挂钩 §ePlaceholderAPI!")
            }
        }
    }

    override fun translateStringColors(string: String): String {
        return ChatColor.translateAlternateColorCodes('&', string)
    }

    companion object {
        private var PAPIHook: PlaceholderAPIHook? = null
    }
}