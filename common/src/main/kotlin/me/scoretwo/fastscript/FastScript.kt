package me.scoretwo.fastscript

import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.api.plugin.ScriptPlugin
import me.scoretwo.fastscript.command.ScriptCommandNexus
import me.scoretwo.fastscript.config.SettingConfig
import me.scoretwo.fastscript.script.ScriptManager
import me.scoretwo.fastscript.utils.Utils
import me.scoretwo.utils.bukkit.configuration.yaml.patchs.getLowerCaseNode
import me.scoretwo.utils.plugin.GlobalPlugin
import me.scoretwo.utils.sender.GlobalPlayer
import me.scoretwo.utils.sender.GlobalSender
import me.scoretwo.utils.syntaxes.StreamUtils
import net.md_5.bungee.api.ChatColor
import java.io.File

class FastScript(val plugin: ScriptPlugin) {

    val commandNexus: ScriptCommandNexus
    val scriptManager: ScriptManager

    fun setPlaceholder(player: GlobalPlayer, string: String) = plugin.setPlaceholder(player, string)

    init {
        instance = this
        me.scoretwo.fastscript.plugin = plugin

        printLogo()

        plugin.server.console.sendMessage(FormatHeader.INFO, "Initializing...")

        commandNexus = ScriptCommandNexus()
        scriptManager = ScriptManager()

        if (!plugin.dataFolder.exists()) {
            plugin.dataFolder.mkdirs()
        }

        SettingConfig.init()
    }

    /**
    * 初始化内置脚本
    * 暂时弃坑
    */
    fun initInternalScripts() {

    }

    fun initLanguageFiles() {
        Utils.saveDefaultResource(File("${plugin.dataFolder}/languages", "en_US.yml"), StreamUtils.getResource("lang/en_US.yml")!!)
        Utils.saveDefaultResource(File("${plugin.dataFolder}/languages", "zh_CN.yml"), StreamUtils.getResource("lang/zh_CN.yml")!!)
    }

    fun onReload() {
        if (!plugin.dataFolder.exists()) {
            plugin.dataFolder.mkdirs()
        }
        plugin.reload()
        initLanguageFiles()
        initInternalScripts()
        scriptManager.loadScripts()
    }

    /**
     * 用于随机点亮 FastScript 的 Logo.
     * 该创意来源于 TrMenu
     * @author Arasple
     */
    fun printLogo() = arrayOf(
       "___________                __   _________            .__        __   ",
       "\\_   _____/____    _______/  |_/   _____/ ___________|__|______/  |_ ",
       " |    __) \\__  \\  /  ___/\\   __\\_____  \\_/ ___\\_  __ \\  \\____ \\   __\\",
       " |     \\   / __ \\_\\___ \\  |  | /        \\  \\___|  | \\/  |  |_> >  |  ",
       " \\___  /  (____  /____  > |__|/_______  /\\___  >__|  |__|   __/|__|  ",
       "     \\/        \\/     \\/              \\/     \\/         |__|         "
   ).let {
        it.forEachIndexed { index, raw ->
            if (raw.isNotBlank()) {
                val line = raw.toCharArray()
                val width = (2..8).random()
                var randomIndex: Int
                do {
                    randomIndex = (2..line.size - width).random()
                } while (String(line.copyOfRange(randomIndex, randomIndex + width)).isBlank())
                val replace = String(line.copyOfRange(randomIndex, randomIndex + width))
                it[index] = String(line).replaceFirst(replace, "§${arrayOf('a', 'b', '2').random()}$replace§8")
            }
        }
        plugin.server.console.sendMessage(it)
    }

    companion object {
        lateinit var instance: FastScript

        fun setBootstrap(plugin: ScriptPlugin) {
            if (::instance.isInitialized) {
                throw UnsupportedOperationException("Cannot redefine instance")
            }
            FastScript(plugin)
        }

    }

}
lateinit var plugin: GlobalPlugin

fun GlobalSender.sendMessage(formatHeader: FormatHeader, strings: Array<String>, colorIndex: Boolean = true) {
    strings.forEach {
        this.sendMessage(formatHeader, it, colorIndex)
    }
}

fun GlobalSender.sendMessage(formatHeader: FormatHeader, string: String, colorIndex: Boolean = true) {
    if (colorIndex)
        this.sendMessage("${SettingConfig.instance.defaultLanguage.getString(SettingConfig.instance.defaultLanguage.getLowerCaseNode("format-header.${formatHeader.name.toLowerCase()}"))}${string}")
    else
        this.sendMessage(
            ChatColor.translateAlternateColorCodes('&',"${
                SettingConfig.instance.defaultLanguage.getString(
                    SettingConfig.instance.defaultLanguage.getLowerCaseNode("format-header.${formatHeader.name.toLowerCase()}"))}${string}"))
}

fun String.setPlaceholder(player: GlobalPlayer): String {
    return FastScript.instance.setPlaceholder(player, this)
}