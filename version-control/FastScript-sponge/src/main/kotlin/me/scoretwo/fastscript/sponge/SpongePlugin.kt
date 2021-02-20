package me.scoretwo.fastscript.sponge

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.api.plugin.ScriptPlugin
import me.scoretwo.fastscript.api.utils.ExecType
import me.scoretwo.fastscript.sendMessage
import me.scoretwo.fastscript.sponge.hook.PlaceholderAPIHook
import me.scoretwo.utils.plugin.GlobalPlugin
import me.scoretwo.utils.sender.GlobalPlayer
import me.scoretwo.utils.sender.GlobalSender
import me.scoretwo.utils.sponge.command.registerSpongeCommands
import me.scoretwo.utils.sponge.command.toSpongePlayer
import me.scoretwo.utils.sponge.command.toSpongeSender
import org.spongepowered.api.Sponge

class SpongePlugin(val plugin: GlobalPlugin): ScriptPlugin(plugin) {

    override fun load() {
        FastScript.setBootstrap(this)
    }

    override fun enable() {
        FastScript.instance.reload("script", "plugin")
        PlaceholderAPIHook.initializePlaceholder()

        FastScript.instance.commandNexus.registerSpongeCommands()
    }

    override fun setPlaceholder(player: GlobalPlayer, string: String): String {
        return PlaceholderAPIHook.parsePlaceholder(string, player.toSpongePlayer())
    }

    override fun toOriginalSender(sender: GlobalSender) = sender.toSpongeSender()

    override fun toOriginalPlayer(player: GlobalPlayer) = player.toSpongePlayer()

    override fun toOriginalServer(): Any? = Sponge.getServer()

    override fun reload() {
        if (PlaceholderAPIHook.initializePlaceholder()) {
            plugin.server.console.sendMessage(FormatHeader.HOOKED, "成功挂钩 §ePlaceholderAPI!")
        }
    }
}