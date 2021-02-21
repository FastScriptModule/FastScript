package me.scoretwo.fastscript.expansion.typeengine

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.expansion.FastScriptExpansion
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.api.script.Script
import me.scoretwo.fastscript.plugin
import me.scoretwo.fastscript.sendMessage
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
                plugin.server.console.sendMessage(FormatHeader.ERROR,"脚本 §c${script.description.name} §7评估时出现错误, 请检查脚本格式.")
                e.printStackTrace()
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
                plugin.server.console.sendMessage(FormatHeader.ERROR,"临时脚本评估时出现错误, 请检查脚本格式.")
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
            plugin.server.console.sendMessage(FormatHeader.ERROR, "脚本 §c${script.description.name} §7执行函数 §8$main §7时发生错误.")
            null
        } catch (e: NoSuchMethodException) {
            if (main == "init") {
                null
            } else {
                plugin.server.console.sendMessage(FormatHeader.ERROR, "临时脚本执行函数 §8$main §7时发生错误, 函数不存在!")
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
            plugin.server.console.sendMessage(FormatHeader.ERROR, "临时脚本执行函数 §8$main §7时发生错误.")
            null
        } catch (e: NoSuchMethodException) {
            if (main == "init") {
                null
            } else {
                plugin.server.console.sendMessage(FormatHeader.ERROR, "临时脚本执行函数 §8$main §7时发生错误, 函数不存在!")
                null
            }
        }
    }


}