package me.scoretwo.fastscript.expansion.typeengine

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.expansion.FastScriptExpansion
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.api.script.Script
import me.scoretwo.fastscript.plugin
import me.scoretwo.fastscript.sendMessage
import me.scoretwo.utils.sender.GlobalSender
import org.apache.commons.lang3.StringUtils
import javax.script.Invocable
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import javax.script.ScriptException

abstract class TypeEngineExpansion: FastScriptExpansion() {

    val engine: ScriptEngine = ScriptEngineManager(plugin.pluginClassLoader).getEngineByName(sign)
    override val needEval = true

    init {
        engine.put("server", plugin.server)
        engine.put("expansionmanager", FastScript.instance.expansionManager)
    }

    override fun eval(script: Script, sender: GlobalSender): Any? {
        if (!script.texts.keys.contains(sign)) return null
        if (sender.isPlayer()) sender.toPlayer().let {
            engine.put("player", it)
            engine.put("originalplayer", plugin.toOriginalPlayer(it!!))
        }
        engine.put("sender", sender)
        engine.put("originalsender", plugin.toOriginalSender(sender))
        return try {
            engine.eval(script.texts[sign].toString())
        } catch (e: ScriptException) {
            plugin.server.console.sendMessage(FormatHeader.ERROR,"脚本 §c${script.description.name} §7解析时出现错误, 请检查脚本格式.")
        }
    }

    override fun eval(text: String, sender: GlobalSender): Any? {
        if (StringUtils.isBlank(text))
            return null
        if (sender.isPlayer()) sender.toPlayer().let {
            engine.put("player", it)
            engine.put("originalplayer", plugin.toOriginalPlayer(it!!))
        }
        engine.put("sender", sender)
        engine.put("originalsender", plugin.toOriginalSender(sender))
        return try {
            engine.eval(text)
        } catch (e: ScriptException) {
            plugin.server.console.sendMessage(FormatHeader.ERROR,"临时脚本解析时出现错误, 请检查脚本格式.")
        }
    }

    override fun execute(script: Script, sender: GlobalSender, main: String, args: Array<Any?>): Any? {
        if (!script.texts.keys.contains(sign)) return null
        return try {
            if (engine !is Invocable)
                eval(script, sender)

            val invocable = engine as Invocable

            invocable.invokeFunction(main, *args)
        } catch (e: Exception) {
            plugin.server.console.sendMessage(
                FormatHeader.ERROR,
                "脚本 §c${script.description.name} §7执行函数 §8$main §7时发生错误."
            )
        }
    }

    override fun execute(text: String, sender: GlobalSender, main: String, args: Array<Any?>): Any? {
        if (StringUtils.isBlank(text))
            return null
        return try {
            if (engine !is Invocable)
                eval(text, sender)

            val invocable = engine as Invocable

            invocable.invokeFunction(main, *args)
        } catch (e: Exception) {
            plugin.server.console.sendMessage(FormatHeader.ERROR, "临时脚本执行函数 §8$main §7时发生错误.")
        }
    }


}