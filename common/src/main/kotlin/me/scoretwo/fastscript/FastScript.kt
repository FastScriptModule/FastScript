package me.scoretwo.fastscript

import me.scoretwo.fastscript.api.expansion.ExpansionManager
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.api.language.LanguageManager
import me.scoretwo.fastscript.api.plugin.ScriptPlugin
import me.scoretwo.fastscript.api.plugin.ScriptPluginState
import me.scoretwo.fastscript.command.FSCommandNexus
import me.scoretwo.fastscript.config.SettingConfig
import me.scoretwo.fastscript.api.script.ScriptManager
import me.scoretwo.fastscript.api.utils.ExecType
import me.scoretwo.fastscript.api.utils.ExecUtils
import me.scoretwo.fastscript.api.utils.maven.MavenArtifact
import me.scoretwo.fastscript.api.utils.process.ProcessResultType
import me.scoretwo.fastscript.command.commands.ScriptCommand
import me.scoretwo.fastscript.utils.Assist
import me.scoretwo.fastscript.utils.assist
import me.scoretwo.utils.sender.GlobalPlayer
import me.scoretwo.utils.sender.GlobalSender
import net.md_5.bungee.api.ChatColor

class FastScript(val plugin: ScriptPlugin) {

    lateinit var commandNexus: FSCommandNexus
    lateinit var scriptManager: ScriptManager
    lateinit var expansionManager: ExpansionManager

    fun setPlaceholder(player: GlobalPlayer, string: String) = plugin.setPlaceholder(player, string)

    private val libs = arrayOf(
        MavenArtifact("org.openjdk.nashorn:nashorn-core:15.2"),
        MavenArtifact("org.ow2.asm:asm-commons:7.3.1"),
        MavenArtifact("org.ow2.asm:asm-analysis:7.3.1"),
        MavenArtifact("org.ow2.asm:asm:7.3.1"),
        MavenArtifact("org.ow2.asm:asm-util:7.3.1"),
        MavenArtifact("org.ow2.asm:asm-tree:7.3.1"),

        MavenArtifact("org.scala-lang:scala-compiler:2.12.9"),
        MavenArtifact("org.scala-lang:scala-library:2.12.9"),
        MavenArtifact("org.scala-lang:scala-reflect:2.12.9"),
        MavenArtifact("org.scala-lang.modules:scala-xml_2.12:1.1.0"),
    )

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
        val startTime = System.currentTimeMillis()
        assist = Assist()
        settings = SettingConfig()
        languages = LanguageManager()
        plugin.server.console.sendMessage(FormatHeader.TREE, "Loaded config and language system.§8(${System.currentTimeMillis() - startTime}ms)")

        ExecUtils.execPeriod(ExecType.Loaded, languages["EXEC-ID.CONFIGS"]) {
            reload("config")
        }

        ExecUtils.execPeriod(ExecType.Loaded, languages["EXEC-ID.LIBS"]) {
            fun download(artifact: MavenArtifact) {
                val start = System.currentTimeMillis()
                val processResult = artifact.download()
                if (processResult.type == ProcessResultType.FAILED) {
                    plugin.server.console.sendMessage(FormatHeader.ERROR, languages["DOWNLOAD-LIBS-FAILED"])
                    throw Throwable(languages["DOWNLOAD-LIBS-FAILED"])
                }
                if (processResult.type == ProcessResultType.SUCCESS) {
                    val format = if (stats == ScriptPluginState.RUNNING) FormatHeader.INFO else FormatHeader.TREE
                    plugin.server.console.sendMessage(format, languages["DOWNLOADED-LIB"].setPlaceholder(mapOf(
                        "lib_name" to "${artifact.artifactId}-${artifact.version}.jar",
                        "millisecond" to "${System.currentTimeMillis() - start}"
                    )))
                }
            }
            libs.forEach { download(it) }
            plugin.libs.forEach { download(it) }
        }

        ExecUtils.execPeriod(ExecType.Initialized, "script manager") {
            scriptManager = ScriptManager()
        }

        ExecUtils.execPeriod(ExecType.Initialized, "expansion manager") {
            expansionManager = ExpansionManager()
        }
        expansionManager.reload()

        ExecUtils.execPeriod(ExecType.Initialized, "CommandNexus") {
            commandNexus = FSCommandNexus()
        }
    }

    fun reloadLanguage() {
        languages.current = languages.languages[settings.getString("Options.Language")]?.reload() ?: languages.defaultLanguage.reload().also {
            plugin.server.console.sendMessage(FormatHeader.ERROR, "Language loading failed. The file may not exist. The default language will be used: ${it.name}")
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
                    // Try to Fix Mohist and CatServer
                    if (::commandNexus.isInitialized) {
                        commandNexus = FSCommandNexus()
                    }
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
        var stats = ScriptPluginState.INITIALIZING

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
        this.sendMessage("${format.toLanguageFormat()}${text}")
    else
        this.sendMessage(
            ChatColor.translateAlternateColorCodes('&', "${format.toLanguageFormat()}${text}"))
}



fun GlobalSender.sendMessage(format: FormatHeader, text: String, placeholders: Map<String, String>) {
    if (format == FormatHeader.DEBUG && !debug)
        return
    this.sendMessage("${format.toLanguageFormat()}$text", placeholders)
}

fun GlobalSender.sendMessage(text: String, placeholders: Map<String, String>) {
    var rawText = text
    placeholders.forEach {
        rawText = rawText.replace("{${it.key}}", it.value)
    }
    this.sendMessage(text)
}

fun String.setPlaceholder(player: GlobalPlayer) = FastScript.instance.setPlaceholder(player, this)


fun String.setPlaceholder(placeholder: Pair<String, String>, player: GlobalPlayer? = null) = this.setPlaceholder(mapOf(placeholder), player)

fun String.setPlaceholder(placeholders: Map<String, String>, player: GlobalPlayer? = null): String {
    var rawText = if (player == null) this else setPlaceholder(player)
    placeholders.forEach {
        rawText = rawText.replace("{${it.key}}", it.value)
    }
    return rawText
}