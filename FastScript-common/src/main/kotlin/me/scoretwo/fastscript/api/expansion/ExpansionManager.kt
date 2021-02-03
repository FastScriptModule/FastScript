package me.scoretwo.fastscript.api.expansion

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.expansion.javascript.JavaScriptExpansion
import me.scoretwo.fastscript.plugin
import me.scoretwo.fastscript.sendMessage
import me.scoretwo.utils.syntaxes.findClass
import java.io.File
import java.net.URL
import java.net.URLClassLoader

class ExpansionManager {

    val expansionFolder = File(plugin.dataFolder, "expansions")
    val expansions = mutableSetOf<FastScriptExpansion>()

    init {
        register(JavaScriptExpansion())

        if (!expansionFolder.exists()) expansionFolder.mkdirs()
    }

    fun register(expansion: FastScriptExpansion) {
        expansions.add(expansion)
    }

    fun unregister(expansion: FastScriptExpansion) {
        expansions.remove(expansion)
    }

    @Synchronized
    fun reload() {
        expansions.clear()

        register(JavaScriptExpansion())

        if (!expansionFolder.exists())
            expansionFolder.mkdirs()

        for (file in expansionFolder.listFiles() ?: arrayOf()) {
            val expansionClass = try {
                file.findClass(FastScriptExpansion::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                plugin.server.console.sendMessage(FormatHeader.ERROR, "An exception occurred when loading extended ${file.name}, the reason:\n${e.message}")
                continue
            }

            if (expansionClass == null) {
                plugin.server.console.sendMessage(FormatHeader.ERROR, "Unable to load the extension ${file.name} because it has no FastScriptExpansion class available!")
                continue
            }

            try {
                expansions.add(expansionClass.newInstance())
            } catch (e: Exception) {
                e.printStackTrace()
                plugin.server.console.sendMessage(FormatHeader.ERROR, "An exception occurred when loading extended ${file.name}, the reason:\n${e.message}")
            }
        }


    }

    fun unregister(name: String) = expansions.forEach {
        if (it.name == name) {
            expansions.remove(it)
            return@forEach
        }
    }

}