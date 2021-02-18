package me.scoretwo.fastscript

import me.scoretwo.fastscript.api.expansion.ExpansionManager
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.api.language.LanguageManager
import me.scoretwo.fastscript.api.plugin.ScriptPlugin
import me.scoretwo.fastscript.api.script.custom.CustomScript
import me.scoretwo.fastscript.command.FSCommandNexus
import me.scoretwo.fastscript.config.SettingConfig
import me.scoretwo.fastscript.api.script.ScriptManager
import me.scoretwo.fastscript.command.commands.ScriptCommand
import me.scoretwo.utils.sender.GlobalPlayer
import me.scoretwo.utils.sender.GlobalSender
import net.md_5.bungee.api.ChatColor

class FastScript(val plugin: ScriptPlugin) {

    val commandNexus: FSCommandNexus
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
        languages = LanguageManager()
        reload("config")

        commandNexus = FSCommandNexus()
        scriptManager = ScriptManager()
        expansionManager = ExpansionManager().also {
            it.reload()
        }
    }

    fun reloadLanguage() {
        languages.current = languages.languages[settings.getString("Options.Language")] ?: languages.defaultLanguage.also {
            plugin.server.console.sendMessage(FormatHeader.ERROR, "Language loading failed. The file may not exist. The default language will be used: en_US")
        }
    }

    /**
    * 初始化内置脚本
    * 暂时弃坑
    */
    fun initInternalScripts() {

    }

    fun reloadAll() = reload("config", "script", "plugin")

    fun reload(vararg modes: String) {
        if (!plugin.dataFolder.exists()) {
            plugin.dataFolder.mkdirs()
        }
        modes.forEach {mode ->
            when (mode) {
                "config" -> {
                    settings.reload()
                    reloadLanguage()
                }
                "script" -> {
                    initInternalScripts()
                    scriptManager.loadScripts()
                    commandNexus.findSubCommand("script")?.also { (it as ScriptCommand).reload() }
                }
                "plugin" ->{
                    plugin.reload()
                }
            }
        }
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
var debug = false

lateinit var plugin: ScriptPlugin

lateinit var settings: SettingConfig
lateinit var languages: LanguageManager

fun GlobalSender.sendMessage(format: FormatHeader, texts: Array<String>, color: Boolean = true) = texts.forEach {
    this.sendMessage(format, it, color)
}

fun GlobalSender.sendMessage(format: FormatHeader, text: String, color: Boolean = true) {
    if (format == FormatHeader.DEBUG && !debug)
        return
    if (!color)
        this.sendMessage("${languages["format-header.${format.name}"]}${text}")
    else
        this.sendMessage(
            ChatColor.translateAlternateColorCodes('&', "${languages["format-header.${format.name}"]}${text}"))
}



fun GlobalSender.sendMessage(format: FormatHeader, text: String, placeholders: Map<String, String>) {
    if (format == FormatHeader.DEBUG && !debug)
        return
    this.sendMessage("${languages["format-header.${format.name}"]}$text", placeholders)
}

fun GlobalSender.sendMessage(text: String, placeholders: Map<String, String>) {
    var rawText = text
    placeholders.forEach {
        rawText = rawText.replace("{${it.key}}", it.value)
    }
    this.sendMessage(text)
}

fun String.setPlaceholder(player: GlobalPlayer) = FastScript.instance.setPlaceholder(player, this)