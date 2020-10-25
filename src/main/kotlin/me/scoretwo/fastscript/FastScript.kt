package me.scoretwo.fastscript

import me.scoretwo.fastscript.api.plugin.FastScriptMain
import me.scoretwo.fastscript.api.script.ScriptManager
import me.scoretwo.fastscript.commands.CommandManager
import me.scoretwo.fastscript.config.SettingConfig
import me.scoretwo.fastscript.utils.Utils
import me.scoretwo.utils.configuration.patchs.getLowerCaseNode
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.net.URL

class FastScript(main: FastScriptMain) {

    private val main: FastScriptMain = main

    val dataFolder: File
    val classLoader: ClassLoader

    val scriptManager: ScriptManager
    val commandManager: CommandManager

    fun hasPermission(sender: Any, string: String) = main.hasPermission(sender, string)
    fun setPlaceholder(player: Any, string: String) = main.setPlaceholder(player, string)
    fun sendMessage(sender: Any, string: String, colorIndex: Boolean) = main.sendMessage(sender, string, colorIndex)
    fun translateStringColors(string: String): String = main.translateStringColors(string)

    init {
        instance = this
        CONSOLE = main.CONSOLE
        printLogo()
        println("FastScript 正在初始化...")

        dataFolder = main.getDataFolder()
        classLoader = main.getPluginClassLoader()
        scriptManager = ScriptManager()
        commandManager = CommandManager()

        if (!dataFolder.exists()) {
            dataFolder.mkdirs()
        }

        SettingConfig.init()
    }

    fun getResource(filename: String): InputStream? {
        return try {
            val url: URL = classLoader.getResource(filename) ?: return null
            val connection = url.openConnection()
            connection.useCaches = false
            connection.getInputStream()
        } catch (ex: IOException) {
            null
        }
    }

    /**
    * 初始化内置脚本
    * 暂时弃坑
    */
    fun initInternalScripts() {

    }

    fun initLanguageFiles() {
        Utils.saveDefaultResource(File("${dataFolder}/languages", "en_US.yml"), getResource("lang/en_US.yml")!!)
        Utils.saveDefaultResource(File("${dataFolder}/languages", "zh_CN.yml"), getResource("lang/zh_CN.yml")!!)
    }

    fun onReload() {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs()
        }
        main.onReload()
        initLanguageFiles()
        initInternalScripts()
        scriptManager.loadScripts()
        commandManager.initCommands()
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
        CONSOLE.sendMessage(it)
    }

    companion object {
        lateinit var instance: FastScript
        var CONSOLE = Any()

        fun setBootstrap(main: FastScriptMain) {
            /*if (initialized) {
                throw UnsupportedOperationException("Cannot redefine instance")
            }*/
            FastScript(main)
        }
/*
        @JvmStatic
        fun main(args: Array<out String>) {

            val main = object : FastScriptMain {
                override val CONSOLE: Any = this

                override fun getDataFolder(): File {
                    return File("FastScript")
                }

                override fun getPluginClassLoader(): ClassLoader {
                    return javaClass.classLoader
                }

                override fun setPlaceholder(player: Any, string: String): String {
                    return string
                }

                override fun onReload() {}

                override fun sendMessage(sender: Any, string: String, colorIndex: Boolean) {
                    printColors(if (colorIndex) string.replace("§", "&") else string)
                }
            }

            instance = FastScript(main)

            instance.printLogo()
        }

        fun printColors(list: MutableList<String>) {
            list.forEach { printColors(it) }
        }

        fun printColors(string: String) {
            for (s in string.split("§")) {
                if (s.isEmpty()) continue
                val index = s.substring(0,1)
                val rawMessage = s.substring(1)

                when(index) {
                    "a"-> print(rawMessage.lightGreen())
                    "b"-> print(rawMessage.lightCyan())
                    "c"-> print(rawMessage.lightRed())
                    "d"-> print(rawMessage.lightMagenta())
                    "e"-> print(rawMessage.lightYellow())
                    "f"-> print(rawMessage.lightWhite())
                    "0"-> print(rawMessage.black())
                    "1"-> print(rawMessage.blue())
                    "2"-> print(rawMessage.green())
                    "3"-> print(rawMessage.cyan())
                    "4"-> print(rawMessage.red())
                    "5"-> print(rawMessage.magenta())
                    "6"-> print(rawMessage.yellow())
                    "7"-> print(rawMessage.lightGray())
                    "8"-> print(rawMessage.lightGray())
                    "9"-> print(rawMessage.lightBlue())
                    else-> print(rawMessage.lightGray())
                }
            }
            println()
        }
*/
    }

}

enum class FormatHeader { INFO, WARN, ERROR, TIPS, HOOKED, DEBUG }

fun Any.hasPermission(string: String): Boolean {
    return FastScript.instance.hasPermission(this, string)
}


fun Any.sendMessage(formatHeader: FormatHeader, strings: Array<String>, colorIndex: Boolean = true) {
    strings.forEach {
        this.sendMessage(formatHeader, it, colorIndex)
    }
}

fun Any.sendMessage(formatHeader: FormatHeader, string: String, colorIndex: Boolean = true) {
    this.sendMessage(
        "${SettingConfig.instance.defaultLanguage.getString(SettingConfig.instance.defaultLanguage.getLowerCaseNode("format-header.${formatHeader.name.toLowerCase()}"))}${string}", colorIndex
    )
}

fun Any.sendMessage(string: String, colorIndex: Boolean = true) {
    FastScript.instance.sendMessage(this, string, colorIndex)
}

fun Any.sendMessage(strings: Array<String>, colorIndex: Boolean = true) {
    strings.forEach {
        this.sendMessage(it, colorIndex)
    }
}

fun String.setPlaceholder(sender: Any): String {
    return FastScript.instance.setPlaceholder(sender, this)
}