package me.scoretwo.fastscript.bukkit

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.api.plugin.ScriptPlugin
import me.scoretwo.fastscript.bukkit.hook.PlaceholderAPIHook
import me.scoretwo.fastscript.languages
import me.scoretwo.fastscript.sendMessage
import me.scoretwo.fastscript.setPlaceholder
import me.scoretwo.utils.bukkit.command.*
import me.scoretwo.utils.bukkit.plugin.toBukkitPlugin
import me.scoretwo.utils.command.executor.Executors
import me.scoretwo.utils.plugin.GlobalPlugin
import me.scoretwo.utils.sender.GlobalPlayer
import me.scoretwo.utils.sender.GlobalSender
import org.apache.commons.lang.StringUtils
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.omg.CORBA.portable.UnknownException

class BukkitPlugin(plugin: GlobalPlugin): ScriptPlugin(plugin) {

    override fun load() {
        FastScript.setBootstrap(this)
    }

    override fun enable() {
        FastScript.instance.commandNexus.findSubCommand("tools")?.also { toolsCommand ->
            toolsCommand.register(
                toolsCommand.nextBuilder().alias("sounds", "sound")
                    .description(languages["COMMAND-NEXUS.COMMANDS.TOOLS.BUKKIT.SOUNDS.DESCRIPTION"])
                    .customCommand("<player>", arrayOf("[sound]", "[volume]", "[pitch]"), languages["COMMAND-NEXUS.COMMANDS.TOOLS.BUKKIT.SOUNDS.DESCRIPTION"])
                    .executor(object : Executors {
                        override fun execute(sender: GlobalSender, parents: Array<String>, args: Array<String>): Boolean {
                            if (args.size < 2) {
                                return false
                            }
                            val sound = try { Sound.valueOf(args[1]) } catch (t: Throwable) {
                                sender.sendMessage(FormatHeader.ERROR, languages["COMMAND-NEXUS.COMMANDS.TOOLS.BUKKIT.SOUNDS.NOT-FOUND-SOUND"].setPlaceholder("sound_name" to args[1]))
                                return true
                            }
                            val volume: Float = try { args[2].toFloat() } catch (t: Throwable) { 1.0F }
                            val pitch: Float = try { args[3].toFloat() } catch (t: Throwable) { 1.0F }
                            if (args[0].toLowerCase() == "@all") {
                                plugin.server.getOnlinePlayers().forEach { it.toBukkitPlayer().also { it.playSound(it.location, sound, volume, pitch) } }
                                return true
                            }
                            val target = plugin.server.getPlayer(args[0]).let {
                                if (it.isPresent) {
                                    it.get()
                                } else {
                                    sender.sendMessage(FormatHeader.ERROR, languages["COMMAND-NEXUS.HELPER.PLAYER-IS-OFFLINE"].setPlaceholder("player_name" to args[0]))
                                    return true
                                }
                            }

                            target.toBukkitPlayer().also { it.playSound(it.location, sound, volume, pitch) }
                            return true
                        }

                        override fun tabComplete(sender: GlobalSender, parents: Array<String>, args: Array<String>): MutableList<String>? {
                            when {
                                args.size < 2 -> {
                                    return mutableListOf("@ALL").also { list -> plugin.server.getOnlinePlayers().forEach { list.add(it.name) } }
                                }
                                args.size < 3 -> {
                                    return mutableListOf<String>().also { list -> Sound.values().forEach { list.add(it.name) } }
                                }
                                args.size < 4 -> {
                                    return mutableListOf("1.0")
                                }
                                args.size < 5 -> {
                                    return mutableListOf("1.0")
                                }
                            }

                            return null
                        }
                    })

            )
        }

        FastScript.instance.reload("script", "plugin")
    }

    override fun setPlaceholder(player: GlobalPlayer, string: String): String {
        var text: String = string
        if (PAPIHook != null) {
            text = PlaceholderAPIHook.setPlaceholder(player.toBukkitPlayer(), string)
        }

        return text
    }

    override fun toOriginalPlugin() = toBukkitPlugin()

    override fun toOriginalSender(sender: GlobalSender) = sender.toBukkitSender()

    override fun toOriginalPlayer(player: GlobalPlayer) = player.toBukkitPlayer()

    override fun toOriginalServer() = Bukkit.getServer()

    override fun toGlobalPlayer(any: Any?): GlobalPlayer {
        if (any !is Player) {
            throw Exception("$any not a player!")
        }
        return any.toGlobalPlayer()
    }

    override fun toGlobalSender(any: Any?): GlobalSender {
        if (any !is Player) {
            throw Exception("$any not a player!")
        }
        return any.toGlobalPlayer()
    }

    override fun registerListener(any: Any?): Boolean {
        if (any !is Listener) {
            return false
        }
        Bukkit.getPluginManager().registerEvents(any, toBukkitPlugin())
        return true
    }

    override fun unregisterListener(any: Any?): Boolean {
        if (any !is Listener) {
            return false
        }
        HandlerList.unregisterAll(any)
        return true
    }

    override fun reload() {
        if (PAPIHook == null) {
            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                PAPIHook = PlaceholderAPIHook(plugin.toBukkitPlugin())
                plugin.server.console.sendMessage(FormatHeader.HOOKED, languages["HOOKED-PLUGIN"].setPlaceholder("plugin_name" to "PlaceholderAPI"))
            }
        }
    }

}

var PAPIHook: PlaceholderAPIHook? = null