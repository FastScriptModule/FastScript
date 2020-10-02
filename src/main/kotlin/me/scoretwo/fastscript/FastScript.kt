package me.scoretwo.fastscript

import com.andreapivetta.kolor.*
import me.scoretwo.fastscript.api.plugin.FastScriptMain
import me.scoretwo.fastscript.api.script.ScriptManager
import me.scoretwo.fastscript.commands.CommandManager
import java.io.File

class FastScript(main: FastScriptMain) {

    private val main: FastScriptMain = main

    val scriptManager = ScriptManager()
    val commandManager = CommandManager()

    val dataFolder = main.getDataFolder()
    val classLoader = main.getPluginClassLoader()


    fun setPlaceholder(player: Any, string: String) = main.setPlaceholder(player, string)
    fun sendMessage(sender: Any, string: String, colorIndex: Boolean) = main.sendMessage(sender, string, colorIndex)
    fun sendMessage(sender: Any, strings: Array<String>, colorIndex: Boolean) = strings.forEach { main.sendMessage(sender, it, colorIndex) }

    init {
        CONSOLE = main.CONSOLE
    }

   /**
    * 初始化内置脚本
    * 暂时弃坑
    */
    fun initInternalScripts() {

    }

    fun onReload() {
        main.onReload()
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
                it[index] = String(line).replaceFirst(replace, "§${arrayOf('9', 'b', '3').random()}$replace§8")
            }
        }
        sendMessage(CONSOLE, it, false)
    }

    companion object {
        lateinit var instance: FastScript
        var CONSOLE = Any()
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

        fun sendMessage(sender: Any, string: String, colorIndex: Boolean = false) {
            instance.sendMessage(sender, string, colorIndex)
        }
        fun sendMessage(sender: Any, strings: Array<String>, colorIndex: Boolean = false) {
            instance.sendMessage(sender, strings, colorIndex)
        }

        fun setBootstrap(main: FastScriptMain) {
            if (instance != null) {
                throw UnsupportedOperationException("Cannot redefine instance")
            }

            instance = FastScript(main)
        }


    }

}