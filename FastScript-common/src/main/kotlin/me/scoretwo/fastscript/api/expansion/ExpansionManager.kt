package me.scoretwo.fastscript.api.expansion

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.api.utils.ExecType
import me.scoretwo.fastscript.api.utils.process.ProcessResult
import me.scoretwo.fastscript.api.utils.process.ProcessResultType
import me.scoretwo.fastscript.expansion.javascript.JavaScriptExpansion
import me.scoretwo.fastscript.plugin
import me.scoretwo.fastscript.sendMessage
import me.scoretwo.utils.bukkit.configuration.yaml.file.YamlConfiguration
import java.io.File
import java.net.URL
import java.net.URLClassLoader
import java.util.jar.JarFile

class ExpansionManager {

    val expansionFolder = File(plugin.dataFolder, "expansions")
    val expansions = mutableSetOf<FastScriptExpansion>()
    val localExpansions = mutableMapOf<ExpansionDescription, FastScriptExpansion>()

    init {
        if (!expansionFolder.exists()) expansionFolder.mkdirs()
    }

    fun register(expansion: FastScriptExpansion) {
        expansions.add(expansion)
    }

    fun unregister(expansion: FastScriptExpansion) {
        expansions.remove(expansion)
    }

    fun getExpansionByName(name: String): FastScriptExpansion? {
        for (expansion in expansions) {
            if (expansion.name == name)
                return expansion
        }
        return null
    }

    fun getExpansionBySign(sign: String): FastScriptExpansion? {
        for (expansion in expansions) {
            if (expansion.sign == sign)
                return expansion
        }
        return null
    }

    @Synchronized
    fun reload() {
        val startTime = System.currentTimeMillis()
        expansions.clear()
        register(JavaScriptExpansion().reload())

        if (!expansionFolder.exists())
            expansionFolder.mkdirs()

        var success = 1
        var fail = 0
        var total = 1

        for (file in expansionFolder.listFiles() ?: arrayOf()) {
            total++
            val rawExpansion = fromFileExpansion(file)

            if (rawExpansion.first.type == ProcessResultType.FAILED) {
                fail++
                continue
            }

            try {
                expansions.add(rawExpansion.second!!.reload())
                success++
            } catch (e: Exception) {
                fail++
                e.printStackTrace()
                plugin.server.console.sendMessage(FormatHeader.ERROR, "An exception occurred when loading extended ${file.name}, reason:\n§8${e.stackTraceToString()}")
            }
        }
        val format = if (FastScript.stats == ExecType.Loaded) FormatHeader.INFO else FormatHeader.TREE

        plugin.server.console.sendMessage(format, "Loaded §b$total §7expansions, §a$success §7successes${if (fail == 0) "" else ", §c$fail §7failures"}.§8(${System.currentTimeMillis() - startTime}ms)")
    }

    private fun fromFileExpansion(file: File): Pair<ProcessResult, FastScriptExpansion?> {
        val description: ExpansionDescription
        val expansionClass = try {
            val url = file.toURI().toURL()
            val method = URLClassLoader::class.java.getDeclaredMethod("addURL", URL::class.java)
            method.isAccessible = true
            method.invoke(plugin.pluginClassLoader, url)

            val jarFile = JarFile(file)
            description = try {
                ExpansionDescription.readConfig(YamlConfiguration().also { it.load(jarFile.getInputStream(jarFile.getJarEntry("expansion.yml")).reader()) })
            } catch (e: Exception) {
                e.printStackTrace()
                plugin.server.console.sendMessage(FormatHeader.ERROR, "An error occurred while loading the expansion '${file.name}' description file, reason:\n§8${e.stackTraceToString()}")
                return Pair(ProcessResult(ProcessResultType.FAILED), null)
            }
            val clazz = try {
                Class.forName(description.main)
            } catch (e: Exception) {
                e.printStackTrace()
                plugin.server.console.sendMessage(FormatHeader.ERROR, "An error occurred while loading the main class ${description.main} of expansion '${file.name}', reason:\n§8${e.stackTraceToString()}")
                return Pair(ProcessResult(ProcessResultType.FAILED), null)
            }

            val instance = try {
                clazz.newInstance()
            } catch (e: Exception) {
                e.printStackTrace()
                plugin.server.console.sendMessage(FormatHeader.ERROR, "An error occurred while loading the main class ${description.main} of expansion '${file.name}', reason:\n§8${e.stackTraceToString()}")
                return Pair(ProcessResult(ProcessResultType.FAILED), null)
            }

            if (instance !is FastScriptExpansion) {
                plugin.server.console.sendMessage(FormatHeader.ERROR, "An error occurred while loading the main class ${description.main} of expansion '${file.name}', reason: §cThe main class does not depend on FastScriptExpansion.")
                return Pair(ProcessResult(ProcessResultType.FAILED), null)
            }

            clazz.asSubclass(FastScriptExpansion::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            plugin.server.console.sendMessage(FormatHeader.ERROR, "An exception occurred when loading extended '${file.name}', reason:\n§8${e.stackTraceToString()}")
            return Pair(ProcessResult(ProcessResultType.FAILED), null)
        }

        if (expansionClass == null) {
            plugin.server.console.sendMessage(FormatHeader.ERROR, "Unable to load the extension '${file.name}' because it has no FastScriptExpansion class available!")
            return Pair(ProcessResult(ProcessResultType.FAILED), null)
        }
        return Pair(ProcessResult(ProcessResultType.SUCCESS), expansionClass.newInstance().also {
            localExpansions[description] = it
        })
    }

    fun unregister(name: String) = expansions.forEach {
        if (it.name == name) {
            expansions.remove(it)
            return@forEach
        }
    }

}