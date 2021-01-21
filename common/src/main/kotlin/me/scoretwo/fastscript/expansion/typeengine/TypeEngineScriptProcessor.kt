package me.scoretwo.fastscript.expansion.typeengine

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.api.script.Script
import me.scoretwo.fastscript.api.script.ScriptProcessor
import me.scoretwo.fastscript.plugin
import me.scoretwo.fastscript.sendMessage
import me.scoretwo.utils.sender.GlobalSender
import javax.script.Invocable
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import javax.script.ScriptException

class TypeEngineScriptProcessor(script: TypeEngineScript, expansion: TypeEngineExpansion): ScriptProcessor(script, expansion) {

    val engine: ScriptEngine

    init {
        engine = ScriptEngineManager(plugin.pluginClassLoader).getEngineByName(script.engineOption.engine)
        script.engineOption.includes.forEach {
            engine.put(it.key, it.value[script])
        }
        script.engineOption.meta.forEach {
            engine.put(it.key, it.value)
        }
        engine.put("server", plugin.server)
        engine.put("expansionmanager", FastScript.instance.expansionManager)
    }

    override fun eval(sender: GlobalSender): Any? {
        if (sender.isPlayer()) sender.toPlayer().let {
            engine.put("player", it)
            engine.put("originalplayer", it)
        }
        engine.put("sender", sender)
        engine.put("originalsender", plugin.toOriginalSender(sender))
        return try {
            engine.eval(script.mergedTexts[expansion.sign].toString())
        } catch (e: ScriptException) {
            plugin.server.console.sendMessage(FormatHeader.ERROR,"脚本 §c${script.description.name} §7解析时出现错误, 请检查脚本格式.")
        }
    }

    override fun execute(sender: GlobalSender, main: String, args: Array<Any?>): Any? {
        return try {
            if (engine !is Invocable)
                eval(sender)

            val invocable = engine as Invocable

            invocable.invokeFunction(main, *args)
        } catch (e: Exception) {
            plugin.server.console.sendMessage(FormatHeader.ERROR,"脚本 §c${script.description.name} §7执行函数 $main 时发生错误.")
        }
    }

}