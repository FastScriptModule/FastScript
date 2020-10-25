package me.scoretwo.fastscript.velocity

import com.google.inject.Inject
import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import me.scoretwo.fastscript.api.plugin.FastScriptMain
import net.kyori.text.serializer.gson.GsonComponentSerializer
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.chat.ComponentSerializer
import java.io.File
import java.nio.file.Path
import java.util.logging.Logger

@Plugin(
    id = "fastscript",
    name = "FastScript",
    version = "1.0.1-SNAPSHOT",
    description = "FastScript is a Spigot plugin, which can run JavaScript-based scripts more efficiently.",
    authors = ["Score2"]
)
class VelocityPlugin: FastScriptMain {

    @Inject
    fun onStart(server: ProxyServer, logger: Logger) {
        VelocityPlugin.server = server
        VelocityPlugin.logger = logger

    }

    @Inject
    @DataDirectory
    lateinit var path: Path

    override val CONSOLE: Any = server.consoleCommandSource

    override fun getDataFolder(): File {
        return path.toFile()
    }

    override fun getPluginClassLoader(): ClassLoader {
        return javaClass.classLoader
    }

    override fun setPlaceholder(player: Any, string: String): String {
        return string
    }

    override fun onReload() {

    }

    override fun sendMessage(sender: Any, string: String, colorIndex: Boolean) {
        asSender(sender)!!.sendMessage(GsonComponentSerializer.INSTANCE.deserialize(ComponentSerializer.toString(*TextComponent.fromLegacyText(string))))
    }

    override fun hasPermission(sender: Any, string: String): Boolean {
        return asSender(sender)!!.hasPermission(string)
    }

    override fun translateStringColors(string: String): String {
        return ChatColor.translateAlternateColorCodes('&', string)
    }

    fun asSender(sender: Any): CommandSource? {
        return sender as? CommandSource
    }

    companion object {
        lateinit var server: ProxyServer
        lateinit var logger: Logger
    }
}