package me.scoretwo.fastscript.velocity

import com.google.inject.Inject
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import me.scoretwo.fastscript.api.plugin.FastScriptPlugin
import net.md_5.bungee.api.ChatColor
import java.io.File
import java.nio.file.Path
import java.util.logging.Logger

@Plugin(
    id = "fastscript",
    name = "FastScript",
    version = "%%version%%",
    description = "%%description%%",
    authors = ["Score2"]
)
class VelocityPlugin: FastScriptPlugin {

    @Inject
    fun onStart(server: ProxyServer, logger: Logger) {
        VelocityPlugin.server = server
        VelocityPlugin.logger = logger

    }

    @Inject
    @DataDirectory
    lateinit var path: Path

    override val console: Any = server.consoleCommandSource

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

    override fun translateStringColors(string: String): String {
        return ChatColor.translateAlternateColorCodes('&', string)
    }

    companion object {
        lateinit var server: ProxyServer
        lateinit var logger: Logger
    }
}