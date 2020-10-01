package me.scoretwo.fastscript

import com.andreapivetta.kolor.*
import me.scoretwo.fastscript.api.plugin.FastScriptMain
import me.scoretwo.fastscript.api.script.ScriptManager
import java.io.File

class FastScript(val dataFolder: File, private val classLoader: ClassLoader) {

    val scriptManager = ScriptManager()

    fun initInternalScripts() {

    }

    fun onReload() {
        initInternalScripts()
    }

   /**
    * 该创意来源于 TrMenu
    * @author Arasple
    */

    fun printLogo() = mutableListOf(
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
        printColors(it)
    }

    companion object {
        lateinit var instance: FastScript
        lateinit var main: FastScriptMain

        @JvmStatic
        fun main(args: Array<out String>) {

            main = object : FastScriptMain {
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

                override fun sendConsoleMessage(message: String) {
                    println(message.yellow())
                }

                override fun sendMessage(sender: Any, string: String) {
                    println(string.yellow())
                }
            }

            instance = FastScript(main.getDataFolder(), main.getPluginClassLoader())

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

        fun setBootstrap(main: FastScriptMain) {
            if (instance != null) {
                throw UnsupportedOperationException("Cannot redefine instance")
            }

            this.main = main
            instance = FastScript(main.getDataFolder(), main.getPluginClassLoader())
        }


    }

}