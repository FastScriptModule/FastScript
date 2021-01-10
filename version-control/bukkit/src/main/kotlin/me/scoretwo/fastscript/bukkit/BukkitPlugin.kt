package me.scoretwo.fastscript.bukkit

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.FormatHeader
import me.scoretwo.fastscript.api.plugin.FastScriptPlugin
import me.scoretwo.fastscript.api.plugin.logging.JavaLogger
import me.scoretwo.fastscript.api.plugin.logging.ScriptLogger
import me.scoretwo.fastscript.bukkit.hook.PlaceholderAPIHook
import me.scoretwo.fastscript.sendMessage
import me.scoretwo.utils.bukkit.command.patchs.toBukkitSender
import me.scoretwo.utils.bukkit.command.patchs.toGlobalSender
import me.scoretwo.utils.command.GlobalSender
import net.md_5.bungee.api.ChatColor
import org.bstats.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandMap
import org.bukkit.command.CommandSender
import org.bukkit.command.SimpleCommandMap
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class BukkitPlugin: JavaPlugin(), FastScriptPlugin {

    override fun onLoad() {
    }

    override fun onEnable() {
        FastScript.setBootstrap(this)
        FastScript.instance.onReload()

        // 暂无计划
        val metrics = Metrics(this, 9014)

        commandMap.register(description.name, object : Command(description.name, "", "/" + description.name, listOf("script","bukkitScript")) {
            override fun execute(sender: CommandSender, label: String, args: Array<String>): Boolean {
                FastScript.instance.commandManager.execute(sender, args)
                return true
            }

            override fun tabComplete(sender: CommandSender, alias: String, args: Array<String>): MutableList<String> {
                return FastScript.instance.commandManager.tabComplete(sender, args)
            }
        })
    }

    override val console = Bukkit.getConsoleSender().toGlobalSender()
    override val scriptLogger = JavaLogger(logger)
    override val pluginClassLoader = super.getClassLoader()

    override fun setPlaceholder(player: GlobalSender, string: String): String {
        var text: String = string
        if (PAPIHook != null) {
            text = PlaceholderAPIHook.setPlaceholder(player.toBukkitSender() as? Player, string)
        }

        return text
    }

    override fun translateStringColors(string: String): String = ChatColor.translateAlternateColorCodes('&', string)

    override fun onReload() {
        if (PAPIHook == null) {
            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                PAPIHook = PlaceholderAPIHook(this)
                FastScript.console.sendMessage(FormatHeader.HOOKED, "成功挂钩 §ePlaceholderAPI!")
            }
        }
    }

    companion object {
        private var PAPIHook: PlaceholderAPIHook? = null
    }

}