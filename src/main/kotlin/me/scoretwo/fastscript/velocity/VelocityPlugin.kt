package me.scoretwo.fastscript.velocity

import com.google.inject.Inject
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.proxy.ProxyServer
import me.scoretwo.fastscript.api.plugin.FastScriptMain
import java.io.File
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

    override val CONSOLE: Any = server.consoleCommandSource

    override fun getDataFolder(): File {
        TODO("Not yet implemented")
    }

    override fun getPluginClassLoader(): ClassLoader {
        TODO("Not yet implemented")
    }

    override fun setPlaceholder(player: Any, string: String): String {
        TODO("Not yet implemented")
    }

    override fun onReload() {
        TODO("Not yet implemented")
    }

    override fun sendMessage(sender: Any, string: String, colorIndex: Boolean) {
        TODO("Not yet implemented")
    }

    override fun hasPermission(sender: Any, string: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun translateStringColors(string: String): String {
        TODO("Not yet implemented")
    }

    companion object {
        lateinit var server: ProxyServer
        lateinit var logger: Logger
    }
}