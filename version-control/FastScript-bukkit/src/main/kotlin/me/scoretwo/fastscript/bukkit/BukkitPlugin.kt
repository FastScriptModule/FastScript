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
import org.apache.commons.lang3.StringUtils
import org.bukkit.Bukkit
import org.bukkit.Sound

class BukkitPlugin(val plugin: GlobalPlugin): ScriptPlugin(plugin) {

    override fun load() {
        FastScript.setBootstrap(this)
    }

    override fun enable() {
        FastScript.instance.reload("script", "plugin")

        FastScript.instance.commandNexus.findSubCommand("tools")?.also { toolsCommand ->
            toolsCommand.register(
                toolsCommand.nextBuilder().alias("sounds", "sound")
                    .description(languages["COMMAND-NEXUS.COMMANDS.TOOLS.BUKKIT.SOUNDS.DESCRIPTION"])
                    .customCommand("sound", arrayOf("volume", "pitch", "player"), "Not more...")
                    .executor(object : Executors {
                        override fun execute(sender: GlobalSender, parents: Array<String>, args: Array<String>): Boolean {
                            if (args.size < 3) {
                                return false
                            }
                            val target = if (args.size < 4) {
                                plugin.server.getPlayer(args[3]).let {
                                    if (it.isPresent)
                                        return@let it.get()
                                    else {
                                        sender.sendMessage(languages["COMMAND-NEXUS.HELPER.PLAYER-IS-OFFLINE"].setPlaceholder("player_name" to args[3]))
                                        return true
                                    }
                                }
                            } else {
                                if (sender.isPlayer())
                                    sender.toPlayer()
                                else
                                    return false
                            }
                            val sound = Sound.valueOf(args[0])
                            val volume: Float = if (StringUtils.isNumeric(args[1])) args[1].toFloat() else 1.0F
                            val pitch: Float = if (StringUtils.isNumeric(args[2])) args[2].toFloat() else 1.0F

                            target?.toBukkitPlayer().also { it?.playSound(it.location, sound, volume, pitch) }
                            return true
                        }

                        override fun tabComplete(sender: GlobalSender, parents: Array<String>, args: Array<String>): MutableList<String>? {
                            when {
                                args.size < 2 -> {
                                    return toolsCommand.findKeywordIndex(args[0], mutableListOf<String>().also { list -> Sound.values().forEach { list.add(it.name) } })
                                }
                                args.size < 3 -> {
                                    return toolsCommand.findKeywordIndex(args[1], mutableListOf("1.0"))
                                }
                                args.size < 4 -> {
                                    return toolsCommand.findKeywordIndex(args[2], mutableListOf("1.0"))
                                }
                            }

                            return null
                        }
                    })

            )
        }

        FastScript.instance.commandNexus.registerBukkitCommands()
    }

    override fun setPlaceholder(player: GlobalPlayer, string: String): String {
        var text: String = string
        if (PAPIHook != null) {
            text = PlaceholderAPIHook.setPlaceholder(player.toBukkitPlayer(), string)
        }

        return text
    }

    override fun toOriginalSender(sender: GlobalSender) = sender.toBukkitSender()

    override fun toOriginalPlayer(player: GlobalPlayer) = player.toBukkitPlayer()

    override fun toOriginalServer() = Bukkit.getServer()

    override fun reload() {
        if (PAPIHook == null) {
            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                PAPIHook = PlaceholderAPIHook(plugin.toBukkitPlugin())
                plugin.server.console.sendMessage(FormatHeader.HOOKED, "成功挂钩 §ePlaceholderAPI!")
            }
        }
    }

}

var PAPIHook: PlaceholderAPIHook? = null