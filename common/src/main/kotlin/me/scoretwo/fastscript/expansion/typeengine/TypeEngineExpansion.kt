package me.scoretwo.fastscript.expansion.typeengine

import me.scoretwo.fastscript.*
import me.scoretwo.fastscript.api.expansion.FastScriptExpansion
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.api.script.Script
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

    override fun reload(): TypeEngineExpansion {
        engine.put("server", plugin.toOriginalServer())
        engine.put("globalServer", plugin.server)
        engine.put("scriptManager", FastScript.instance.scriptManager)
        engine.put("expansionManager", FastScript.instance.expansionManager)
        return this
    }

    override fun eval(script: Script, sender: GlobalSender, vararg args: String): Any? {
        if (!script.texts.keys.contains(sign))
            return null
        if (sender.isPlayer()) {
            sender.toPlayer().let {
                engine.put("globalPlayer", it)
                engine.put("player", plugin.toOriginalPlayer(it!!))
            }
        } else {
            engine.put("globalPlayer", null)
            engine.put("player", null)
        }
        engine.put("globalSender", sender)
        engine.put("sender", plugin.toOriginalSender(sender))
        engine.put("args", args)
        return let {
            try {
                engine.eval(script.texts[sign]).also {
                    if (script.texts[sign]?.contains(it?.toString() ?: "") == true) return@let "EVALUATED"
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

    override fun eval(text: String, sender: GlobalSender, vararg args: String): Any? {
        if (StringUtils.isBlank(text))
            return null
        if (sender.isPlayer()) {
            sender.toPlayer().let {
                engine.put("globalPlayer", it)
                engine.put("player", plugin.toOriginalPlayer(it!!))
            }
        } else {
            engine.put("globalPlayer", null)
            engine.put("player", null)
        }
        engine.put("globalSender", sender)
        engine.put("sender", plugin.toOriginalSender(sender))
        engine.put("args", args)
        return let {
            try {
                engine.eval(text).also {
                    if (text.contains(it?.toString() ?: "")) return@let "EVALUATED"
                }
            } catch (e: ScriptException) {
                plugin.server.console.sendMessage(FormatHeader.ERROR, languages["EXPANSION.TYPE-ENGINE.EVALUATE-TEMP-SCRIPT-ERROR"].setPlaceholder(
                    mapOf("reason" to e.stackTraceToString())
                ))
                null
            }
        }
    }

    override fun execute(script: Script, sender: GlobalSender, main: String, args: Array<Any?>): Any? {
        if (!script.texts.keys.contains(sign))
            return null
        return try {
            if (needEval)
                eval(script, sender)

            val invocable = engine as Invocable

            invocable.invokeFunction(main, *args)
        } catch (e: ScriptException) {
            plugin.server.console.sendMessage(FormatHeader.ERROR, languages["EXPANSION.TYPE-ENGINE.EXECUTE-SCRIPT-ERROR"].setPlaceholder(
                mapOf(
                    "script_name" to script.name,
                    "execute_main" to main,
                    "reason" to e.stackTraceToString()
                ))
            )
            null
        } catch (e: NoSuchMethodException) {
            if (main == "init") {
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

    override fun execute(text: String, sender: GlobalSender, main: String, args: Array<Any?>): Any? {
        if (StringUtils.isBlank(text))
            return null
        return try {
            if (needEval)
                eval(text, sender)

            val invocable = engine as Invocable

            invocable.invokeFunction(main, *args)
        } catch (e: ScriptException) {
            plugin.server.console.sendMessage(FormatHeader.ERROR, languages["EXPANSION.TYPE-ENGINE.EXECUTE-TEMP-SCRIPT-ERROR"].setPlaceholder(
                mapOf(
                    "execute_main" to main,
                    "reason" to e.stackTraceToString()
                ))
            )
            null
        } catch (e: NoSuchMethodException) {
            if (main == "init") {
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