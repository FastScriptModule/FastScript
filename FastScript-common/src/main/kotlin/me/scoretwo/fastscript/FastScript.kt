package me.scoretwo.fastscript

import me.scoretwo.fastscript.api.expansion.ExpansionManager
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.api.language.LanguageManager
import me.scoretwo.fastscript.api.plugin.ScriptPlugin
import me.scoretwo.fastscript.api.script.Script
import me.scoretwo.fastscript.command.ScriptCommandNexus
import me.scoretwo.fastscript.config.SettingConfig
import me.scoretwo.fastscript.api.script.ScriptManager
import me.scoretwo.fastscript.utils.Utils
import me.scoretwo.utils.sender.GlobalPlayer
import me.scoretwo.utils.sender.GlobalSender
import me.scoretwo.utils.syntaxes.StreamUtils
import net.md_5.bungee.api.ChatColor
import java.io.File

class FastScript(val plugin: ScriptPlugin) {

    val commandNexus: ScriptCommandNexus
    val scriptManager: ScriptManager
    val expansionManager: ExpansionManager

    fun setPlaceholder(player: GlobalPlayer, string: String) = plugin.setPlaceholder(player, string)

    init {
        instance = this
        me.scoretwo.fastscript.plugin = plugin

        arrayOf(
            "___________                __   _________            .__        __   ",
            "\\_   _____/____    _______/  |_/   _____/ ___________|__|______/  |_ ",
            " |    __) \\__  \\  /  ___/\\   __\\_____  \\_/ ___\\_  __ \\  \\____ \\   __\\",
            " |     \\   / __ \\_\\___ \\  |  | /        \\  \\___|  | \\/  |  |_> >  |  ",
            " \\___  /  (____  /____  > |__|/_______  /\\___  >__|  |__|   __/|__|  ",
            "     \\/        \\/     \\/              \\/     \\/         |__|         ",
            ""
        ).forEach {
            plugin.server.console.sendMessage("§3$it")
        }

        plugin.server.console.sendMessage("§3FastScript[§bV${plugin.description.version}§3] initializing...")

        if (!plugin.dataFolder.exists()) {
            plugin.dataFolder.mkdirs()
        }

        settings = SettingConfig()

        language = LanguageManager()
        language.current = language.languages[settings.getString("Options.Language")] ?: language.defaultLanguage.also {
            plugin.server.console.sendMessage(FormatHeader.ERROR, "Language loading failed. The file may not exist. The default language will be used: en_US")
        }

        commandNexus = ScriptCommandNexus()
        scriptManager = ScriptManager()
        expansionManager = ExpansionManager()
    }

    /**
    * 初始化内置脚本
    * 暂时弃坑
    */
    fun initInternalScripts() {

    }

    fun onReload() {
        if (!plugin.dataFolder.exists()) {
            plugin.dataFolder.mkdirs()
        }
        plugin.reload()
        expansionManager.reload()
        initInternalScripts()
        scriptManager.loadScripts()
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
lateinit var plugin: ScriptPlugin
val scripts = mutableListOf<Script>()

lateinit var settings: SettingConfig
lateinit var language: LanguageManager

fun GlobalSender.sendMessage(formatHeader: FormatHeader, strings: Array<String>, colorIndex: Boolean = true) {
    strings.forEach {
        this.sendMessage(formatHeader, it, colorIndex)
    }
}

fun GlobalSender.sendMessage(formatHeader: FormatHeader, string: String, colorIndex: Boolean = true) {
    if (colorIndex)
        this.sendMessage("${language["format-header.${formatHeader.name}"]}${string}")
    else
        this.sendMessage(
            ChatColor.translateAlternateColorCodes('&',"${"${language["format-header.${formatHeader.name}"]}${string}"}${string}"))
}

fun String.setPlaceholder(player: GlobalPlayer): String {
    return FastScript.instance.setPlaceholder(player, this)
}