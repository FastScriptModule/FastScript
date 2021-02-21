package me.scoretwo.fastscript.bukkit.hook

import me.clip.placeholderapi.PlaceholderAPI
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import me.scoretwo.fastscript.placeholder.Placeholders
import me.scoretwo.utils.bukkit.command.toGlobalPlayer
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class PlaceholderAPIHook(val plugin: Plugin): PlaceholderExpansion() {

    override fun persist(): Boolean {
        return true
    }

    override fun getIdentifier(): String {
        return plugin.description.name.toLowerCase()
    }

    override fun getAuthor(): String {
        return plugin.description.authors.toString()
    }

    override fun getVersion(): String {
        return plugin.description.version
    }

    override fun onPlaceholderRequest(player: Player, params: String): String {
        return Placeholders.parse(player.toGlobalPlayer(), params)
    }

    companion object {
        fun setPlaceholder(player: Player?, string: String): String = PlaceholderAPI.setPlaceholders(player, string)
    }
}