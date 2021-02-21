package me.scoretwo.fastscript.sponge.hook

import me.rojo8399.placeholderapi.PlaceholderService
import me.scoretwo.fastscript.placeholder.Placeholders
import me.scoretwo.utils.sponge.command.toGlobalPlayer
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
            return Placeholders.parse(player.toGlobalPlayer(), text)
        }
        return placeholderService.replaceSourcePlaceholders(text, player).toPlain()
    }

}