package me.scoretwo.fastscript.sponge

import me.rojo8399.placeholderapi.PlaceholderService
import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.FormatHeader
import me.scoretwo.fastscript.api.plugin.FastScriptMain
import me.scoretwo.fastscript.sendMessage
import me.scoretwo.fastscript.sponge.hook.PlaceholderAPIHook
import org.spongepowered.api.Sponge
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.CommandSource
import org.spongepowered.api.command.args.CommandContext
import org.spongepowered.api.command.args.GenericArguments.plugin
import org.spongepowered.api.command.spec.CommandExecutor
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
    description = "FastScript is a Spigot plugin, which can run JavaScript-based scripts more efficiently.",
    dependencies = [Dependency(id = "placeholderapi", optional = true)]
)
class SpongePlugin: FastScriptMain {

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

    override val CONSOLE: Any = Sponge.getServer().console

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
                FastScript.CONSOLE.sendMessage(FormatHeader.HOOKED, "成功挂钩 §ePlaceholderAPI!")
            }
        }
    }

    override fun sendMessage(sender: Any, string: String, colorIndex: Boolean) {
        asSender(sender)?.sendMessage(Text.of(if (colorIndex) translateStringColors(string) else string))
    }

    override fun hasPermission(sender: Any, string: String): Boolean {
        return asSender(sender)?.hasPermission(string)!!
    }

    override fun translateStringColors(string: String): String {
        return string.replace("§", "&")
    }

    fun asSender(sender: Any): CommandSource? {
        return sender as? CommandSource
    }

    companion object {
        private var PAPIHook: PlaceholderAPIHook? = null
    }
}