package me.scoretwo.fastscript.sponge

import me.rojo8399.placeholderapi.PlaceholderService
import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.api.plugin.ScriptPlugin
import me.scoretwo.fastscript.sendMessage
import me.scoretwo.fastscript.sponge.hook.PlaceholderAPIHook
import me.scoretwo.utils.plugin.GlobalPlugin
import me.scoretwo.utils.sender.GlobalPlayer
import me.scoretwo.utils.sender.GlobalSender
import me.scoretwo.utils.sponge.command.registerSpongeCommands
import me.scoretwo.utils.sponge.command.toGlobalSender
import me.scoretwo.utils.sponge.command.toSpongePlayer
import me.scoretwo.utils.sponge.command.toSpongeSender
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

class SpongePlugin(val plugin: GlobalPlugin): ScriptPlugin(plugin) {

    override fun load() {
        FastScript.setBootstrap(this)
    }

    override fun enable() {
        FastScript.instance.onReload()
        PlaceholderAPIHook.placeholderService = Sponge.getServiceManager().provideUnchecked(PlaceholderService::class.java)

        FastScript.instance.commandNexus.registerSpongeCommands()
    }

    override fun setPlaceholder(player: GlobalPlayer, string: String): String {
        return PlaceholderAPIHook.placeholderService.replaceSourcePlaceholders(string, player.toSpongePlayer()).toPlain()
    }

    override fun toOriginalSender(sender: GlobalSender) = sender.toSpongeSender()

    override fun toOriginalPlayer(player: GlobalPlayer) = player.toSpongePlayer()

    override fun toOriginalServer(): Any? = Sponge.getServer()

    override fun reload() {
        if (PAPIHook == null) {
            if (Sponge.getPluginManager().isLoaded("placeholderapi")) {
                PAPIHook = PlaceholderAPIHook(this)
                plugin.server.console.sendMessage(FormatHeader.HOOKED, "成功挂钩 §ePlaceholderAPI!")
            }
        }
    }

    companion object {
        private var PAPIHook: PlaceholderAPIHook? = null
    }
}