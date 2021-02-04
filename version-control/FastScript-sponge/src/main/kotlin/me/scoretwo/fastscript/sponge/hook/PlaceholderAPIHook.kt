package me.scoretwo.fastscript.sponge.hook

import me.rojo8399.placeholderapi.PlaceholderService
import me.scoretwo.fastscript.api.plugin.ScriptPlugin
import me.scoretwo.utils.sponge.command.toSpongePlayer
import org.spongepowered.api.Sponge
import org.spongepowered.api.entity.living.player.Player

object PlaceholderAPIHook {

    lateinit var placeholderService: PlaceholderService

    fun initializePlaceholder(): Boolean {
        if (Sponge.getPluginManager().isLoaded("placeholderapi")) {
            placeholderService = Sponge.getServiceManager().provideUnchecked(PlaceholderService::class.java)
            return true
        }
        return false
    }

    fun parsePlaceholder(text: String, player: Player): String {
        if (!::placeholderService.isInitialized && initializePlaceholder()) {
            return text
        }
        return placeholderService.replaceSourcePlaceholders(text, player).toPlain()
    }

}