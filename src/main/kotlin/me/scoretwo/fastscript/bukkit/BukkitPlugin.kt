package me.scoretwo.fastscript.bukkit

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.FormatHeader
import me.scoretwo.fastscript.api.plugin.FastScriptMain
import me.scoretwo.fastscript.bukkit.hook.PlaceholderAPIHook
import me.scoretwo.fastscript.sendMessage
import net.md_5.bungee.api.ChatColor
import org.bstats.bukkit.Metrics
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandMap
import org.bukkit.command.CommandSender
import org.bukkit.command.SimpleCommandMap
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class BukkitPlugin: JavaPlugin(), FastScriptMain {

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

    override val CONSOLE: Any = Bukkit.getConsoleSender()

    override fun getPluginClassLoader(): ClassLoader {
        return super.getClassLoader()
    }

    override fun setPlaceholder(player: Any, string: String): String {
        var text: String = string
        if (PAPIHook != null) {
            text = PlaceholderAPIHook.setPlaceholder(player as? Player, string)
        }

        return text
    }

    override fun sendMessage(sender: Any, string: String, colorIndex: Boolean) {
        asSender(sender)?.sendMessage(if (colorIndex) translateStringColors(string) else string)
    }

    override fun hasPermission(sender: Any, string: String): Boolean {
        return asSender(sender)?.hasPermission(string)!!
    }

    override fun translateStringColors(string: String): String {
        return ChatColor.translateAlternateColorCodes('&', string)
    }

    fun asSender(sender: Any): CommandSender? {
        return sender as? CommandSender
    }

    override fun onReload() {
        if (PAPIHook == null) {
            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                PAPIHook = PlaceholderAPIHook(this)
                FastScript.CONSOLE.sendMessage(FormatHeader.HOOKED,"成功挂钩 §ePlaceholderAPI!")
            }
        }
    }

    companion object {
        private var PAPIHook: PlaceholderAPIHook? = null

        val commandMap: CommandMap = Bukkit.getServer().javaClass.getDeclaredMethod("getCommandMap").invoke(Bukkit.getServer()) as SimpleCommandMap
    }

}