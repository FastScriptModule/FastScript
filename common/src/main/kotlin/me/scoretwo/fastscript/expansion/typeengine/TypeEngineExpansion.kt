package me.scoretwo.fastscript.expansion.typeengine

import me.scoretwo.fastscript.*
import me.scoretwo.fastscript.api.expansion.FastScriptExpansion
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.api.script.Script
import me.scoretwo.fastscript.api.script.custom.CustomScript
import me.scoretwo.fastscript.utils.assist
import me.scoretwo.utils.sender.GlobalSender
import org.apache.commons.lang.StringUtils
import javax.script.Invocable
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import javax.script.ScriptException

abstract class TypeEngineExpansion: FastScriptExpansion() {
    abstract val engine: ScriptEngine
    override val needEval = true

    val scriptEngineManager = ScriptEngineManager(plugin.pluginClassLoader)

    private val engineTexts = mutableMapOf<String, ScriptEngine>()
    private val engineScripts = mutableMapOf<Script, ScriptEngine>()

    override fun reload(): TypeEngineExpansion {
        return this
    }

    override fun eval(script: Script, sender: GlobalSender, args: Array<Any?>, otherBindings: Map<String, Any?>): Any? {
        val newEngine = engine.factory.scriptEngine
        if (!script.texts.keys.contains(sign))
            return null

        newEngine.put("meta", HashMap<String, Any?>())
        newEngine.put("plugin", plugin.toOriginalPlugin())
        newEngine.put("server", plugin.toOriginalServer())
        newEngine.put("globalServer", plugin.server)
        newEngine.put("scriptManager", FastScript.instance.scriptManager)
        newEngine.put("expansionManager", FastScript.instance.expansionManager)

        if (sender.isPlayer()) {
            sender.toPlayer().let {
                newEngine.put("globalPlayer", it)
                newEngine.put("player", plugin.toOriginalPlayer(it!!))
            }
        } else {
            newEngine.put("globalPlayer", null)
            newEngine.put("player", null)
        }
        newEngine.put("globalSender", sender)
        newEngine.put("sender", plugin.toOriginalSender(sender))
        newEngine.put("args", args)
        newEngine.put("utils", assist)
        newEngine.put("util", assist)
        newEngine.put("registerListener", script::registerListener)
        newEngine.put("unregisterListener", script::unregisterListener)
        otherBindings.forEach { newEngine.put(it.key, it.value) }

        engineScripts[script] = newEngine
        return let {
            try {
                newEngine.eval(script.texts[sign]).also {
                    if (script.texts[sign]?.contains(it?.toString() ?: "") == true) return@let languages["SUBSTANTIVE.EVALUATED"].toUpperCase()
                }
            } catch (e: ScriptException) {
                plugin.server.console.sendMessage(FormatHeader.ERROR, languages["EXPANSION.TYPE-ENGINE.EVALUATE-SCRIPT-ERROR"].setPlaceholder(
                    mapOf(
                        "script_name" to script.name,
                        "reason" to e.stackTraceToString()
                    )
                ))
                null
            }
        }
    }

    override fun eval(text: String, sender: GlobalSender, args: Array<Any?>, otherBindings: Map<String, Any?>): Any? {
        val newEngine = engine.factory.scriptEngine
        if (text.isBlank())
            return null

        newEngine.put("meta", HashMap<String, Any?>())
        newEngine.put("plugin", plugin.toOriginalPlugin())
        newEngine.put("server", plugin.toOriginalServer())
        newEngine.put("globalServer", plugin.server)
        newEngine.put("globalPlugin", plugin)
        newEngine.put("scriptManager", FastScript.instance.scriptManager)
        newEngine.put("expansionManager", FastScript.instance.expansionManager)

        if (sender.isPlayer()) {
            sender.toPlayer().let {
                newEngine.put("globalPlayer", it)
                newEngine.put("player", plugin.toOriginalPlayer(it!!))
            }
        } else {
            newEngine.put("globalPlayer", null)
            newEngine.put("player", null)
        }
        newEngine.put("globalSender", sender)
        newEngine.put("sender", plugin.toOriginalSender(sender))
        newEngine.put("args", args)
        newEngine.put("utils", assist)
        newEngine.put("util", assist)
        otherBindings.forEach { newEngine.put(it.key, it.value) }

        engineTexts[text] = newEngine
        return let {
            try {
                newEngine.eval(text).also {
                    if (text.contains(it?.toString() ?: "")) return@let languages["SUBSTANTIVE.EVALUATED"].toUpperCase()
                }
            } catch (e: ScriptException) {
                plugin.server.console.sendMessage(FormatHeader.ERROR, languages["EXPANSION.TYPE-ENGINE.EVALUATE-TEMP-SCRIPT-ERROR"].setPlaceholder(
                    mapOf("reason" to e.stackTraceToString())
                ))
                null
            }
        }
    }

    override fun execute(script: Script, sender: GlobalSender, main: String, args: Array<Any?>, otherBindings: Map<String, Any?>): Any? {
        if (!script.texts.keys.contains(sign))
            return null
        return try {
            if (engineScripts[script] !is Invocable)
                eval(script, sender, arrayOf(), otherBindings)

            val invocable = engineScripts[script] as Invocable

            invocable.invokeFunction(main, *args)
        } catch (e: ScriptException) {
            if (main == "load" || main == "unload") {
                null
            } else {
                plugin.server.console.sendMessage(FormatHeader.ERROR, languages["EXPANSION.TYPE-ENGINE.EXECUTE-SCRIPT-ERROR"].setPlaceholder(
                    mapOf(
                        "script_name" to script.name,
                        "execute_main" to main,
                        "reason" to e.stackTraceToString()
                    ))
                )
                null
            }

        } catch (e: NoSuchMethodException) {
            if (main == "load" || main == "unload") {
                null
            } else {
                plugin.server.console.sendMessage(FormatHeader.ERROR, languages["EXPANSION.TYPE-ENGINE.EXECUTE-SCRIPT-FUNCTION-NOT-FOUND-ERROR"].setPlaceholder(
                    mapOf(
                        "script_name" to script.name,
                        "execute_main" to main
                    ))
                )
                null
            }
        }
    }

    override fun execute(text: String, sender: GlobalSender, main: String, args: Array<Any?>, otherBindings: Map<String, Any?>): Any? {
        if (text.isBlank())
            return null
        return try {
            if (engineTexts[text] !is Invocable)
                eval(text, sender, arrayOf(), otherBindings)

            val invocable = engineTexts[text] as Invocable

            invocable.invokeFunction(main, *args)
        } catch (e: ScriptException) {
            if (main == "load" || main == "unload") {
                null
            } else {
                plugin.server.console.sendMessage(FormatHeader.ERROR, languages["EXPANSION.TYPE-ENGINE.EXECUTE-TEMP-SCRIPT-ERROR"].setPlaceholder(
                    mapOf(
                        "execute_main" to main,
                        "reason" to e.stackTraceToString()
                    ))
                )
                null
            }
        } catch (e: NoSuchMethodException) {
            if (main == "load" || main == "unload") {
                null
            } else {
                plugin.server.console.sendMessage(FormatHeader.ERROR, languages["EXPANSION.TYPE-ENGINE.EXECUTE-TEMP-SCRIPT-FUNCTION-NOT-FOUND-ERROR"].setPlaceholder(
                    mapOf("execute_main" to main))
                )
                null
            }
        }
    }


}