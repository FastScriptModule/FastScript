package me.scoretwo.fastscript.expansion.javascript

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.expansion.FastScriptExpansion
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.api.script.FileScript
import me.scoretwo.fastscript.api.script.ScriptDescription
import me.scoretwo.fastscript.plugin
import me.scoretwo.fastscript.sendMessage
import me.scoretwo.utils.sender.GlobalSender
import me.scoretwo.utils.syntaxes.readString
import java.io.File
import javax.script.Invocable
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import javax.script.ScriptException

class JavaScript(
    expansion: FastScriptExpansion,
    description: ScriptDescription,
    val javaScriptOptions: JavaScriptOptions,
    files: MutableList<File>
): FileScript(expansion, description, javaScriptOptions, files) {

    val engine: ScriptEngine
    val mergedString: String

    init {
        mergedString = StringBuilder().also { builder ->
            files.forEach {
                builder.append(it.readString())
                builder.append("\n")
            }
        }.toString()
        engine = ScriptEngineManager(plugin.pluginClassLoader).getEngineByName(javaScriptOptions.engine)

        javaScriptOptions.includes.forEach {
            engine.put(it.key, it.value[this])
        }
        meta.forEach {
            engine.put(it.key, it.value)
        }
        engine.put("server", plugin.server)
        engine.put("expansionmanager", FastScript.instance.expansionManager)
    }
    fun directEval(sender: GlobalSender): Any? {
        if (sender.isPlayer()) sender.toPlayer().let {
            engine.put("player", it)
            engine.put("originalplayer", it)
        }
        engine.put("sender", sender)
        engine.put("originalsender", plugin.toOriginalSender(sender))
        return try {
            engine.eval(mergedString)
        } catch (e: ScriptException) {
            plugin.server.console.sendMessage(FormatHeader.ERROR,"脚本 §c${description.name} §7解析时出现错误, 请检查脚本格式.")
        }
    }

    fun execute(sender: GlobalSender, function: String = javaScriptOptions.main, args: Array<Any?> = arrayOf()): Any? {
        /*if (engine !is Invocable)*/
        return try {
            directEval(sender)

            val invocable = engine as Invocable

            invocable.invokeFunction(function, *args)
        } catch (e: Exception) {
            plugin.server.console.sendMessage(FormatHeader.ERROR,"脚本 §c${description.name} §7执行函数 $function 时发生错误.")
        }
    }


}