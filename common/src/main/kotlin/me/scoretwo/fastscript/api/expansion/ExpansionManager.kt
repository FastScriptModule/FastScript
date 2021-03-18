package me.scoretwo.fastscript.api.expansion

import me.scoretwo.fastscript.*
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.api.plugin.ScriptPluginState
import me.scoretwo.fastscript.api.utils.process.ProcessResult
import me.scoretwo.fastscript.api.utils.process.ProcessResultType
import me.scoretwo.fastscript.expansion.javascript.JavaScriptExpansion
import me.scoretwo.fastscript.expansion.scala.ScalaExpansion
import me.scoretwo.utils.bukkit.configuration.yaml.file.YamlConfiguration
import me.scoretwo.utils.bukkit.configuration.yaml.patchs.ignoreCase
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
            if (expansion.name.equals(name, ignoreCase = true))
                return expansion
        }
        return null
    }

    fun getExpansionBySign(sign: String): FastScriptExpansion? {
        for (expansion in expansions) {
            if (expansion.sign.equals(sign, ignoreCase = true))
                return expansion
        }
        return null
    }

    @Synchronized
    fun reload() {
        val startTime = System.currentTimeMillis()
        expansions.clear()
        var success = 1
        var fail = 0
        var total = 1
        try {
            if (settings.getBoolean(settings.ignoreCase("Options.Internal-Expansions.JavaScript")))
                register(JavaScriptExpansion().reload())
            if (settings.getBoolean(settings.ignoreCase("Options.Internal-Expansions.Scala")))
                register(ScalaExpansion().reload())
        } catch (t: Throwable) {
            fail++
            plugin.server.console.sendMessage(FormatHeader.ERROR, languages["EXPANSION.ERROR-BY-CAUSE.LOAD-ERROR"].setPlaceholder(
                mapOf(
                    "file_name" to "InternalExpansion",
                    "reason" to t.stackTraceToString()
                )
            ))
        }

        if (!expansionFolder.exists())
            expansionFolder.mkdirs()


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
            } catch (t: Throwable) {
                fail++
//                plugin.server.console.sendMessage(FormatHeader.ERROR, "An exception occurred while loading expansion ${file.name}, reason:\n§8${t.stackTraceToString()}")
            }
        }
        val format = if (FastScript.stats == ScriptPluginState.RUNNING) FormatHeader.INFO else FormatHeader.TREE

        val placeholders = mapOf(
            "id" to "expansions",
            "total" to "$total",
            "success" to "$success",
            "fail" to "$fail",
            "millisecond" to "${System.currentTimeMillis() - startTime}"
        )
        if (fail == 0)
            plugin.server.console.sendMessage(format, languages["LOADED-COUNTS-PROCESS-SUCCESS"].setPlaceholder(placeholders))
        else
            plugin.server.console.sendMessage(format, languages["LOADED-COUNTS-PROCESS-SUCCESS-HAS-FAILED"].setPlaceholder(placeholders))
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
            } catch (t: Throwable) {
                plugin.server.console.sendMessage(FormatHeader.ERROR, languages["EXPANSION.ERROR-BY-CAUSE.LOAD-DESCRIPTION-FILE-ERROR"].setPlaceholder(
                    mapOf(
                        "file_name" to file.name,
                        "reason" to t.stackTraceToString()
                    )
                ))
                return Pair(ProcessResult(ProcessResultType.FAILED), null)
            }
            val clazz = try {
                Class.forName(description.main)
            } catch (t: Throwable) {
                plugin.server.console.sendMessage(FormatHeader.ERROR, languages["EXPANSION.ERROR-BY-CAUSE.LOAD-MAIN-CLASS-ERROR"].setPlaceholder(
                    mapOf(
                        "file_name" to file.name,
                        "description_main" to description.main,
                        "reason" to t.stackTraceToString()
                    )
                ))
                return Pair(ProcessResult(ProcessResultType.FAILED), null)
            }

            val instance = try {
                clazz.newInstance()
            } catch (t: Throwable) {
                plugin.server.console.sendMessage(FormatHeader.ERROR, languages["EXPANSION.ERROR-BY-CAUSE.LOAD-MAIN-CLASS-ERROR"].setPlaceholder(
                    mapOf(
                        "file_name" to file.name,
                        "description_main" to description.main,
                        "reason" to t.stackTraceToString()
                    )
                ))
                return Pair(ProcessResult(ProcessResultType.FAILED), null)
            }

            if (instance !is FastScriptExpansion) {
                plugin.server.console.sendMessage(FormatHeader.ERROR, languages["EXPANSION.ERROR-BY-CAUSE.LOAD-MAIN-CLASS-MAIN-NOT-DEPEND"].setPlaceholder(
                    mapOf(
                        "file_name" to file.name,
                        "description_main" to description.main
                    )
                ))
                return Pair(ProcessResult(ProcessResultType.FAILED), null)
            }

            clazz.asSubclass(FastScriptExpansion::class.java)
        } catch (t: Throwable) {
            plugin.server.console.sendMessage(FormatHeader.ERROR, languages["EXPANSION.ERROR-BY-CAUSE.LOAD-ERROR"].setPlaceholder(
                mapOf(
                    "file_name" to file.name,
                    "reason" to t.stackTraceToString()
                )
            ))
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