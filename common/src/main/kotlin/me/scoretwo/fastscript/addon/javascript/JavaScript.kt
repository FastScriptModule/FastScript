package me.scoretwo.fastscript.addon.javascript

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.api.script.ConfigScriptOptions
import me.scoretwo.fastscript.api.script.FileScript
import me.scoretwo.fastscript.api.script.ScriptDescription
import me.scoretwo.fastscript.plugin
import me.scoretwo.fastscript.sendMessage
import me.scoretwo.utils.syntaxes.readString
import java.io.File
import javax.script.Invocable
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import javax.script.ScriptException

class JavaScript(
    description: ScriptDescription,
    val options: JavaScriptOptions,
    files: MutableList<File>
): FileScript(description, options, files) {

    val engine: ScriptEngine
    val mergedString: String

    init {
        mergedString = StringBuilder().also { builder ->
            files.forEach {
                builder.append(it.readString())
                builder.append("\n")
            }
        }.toString()
        engine = ScriptEngineManager(plugin.pluginClassLoader).getEngineByName(options.engine)

        options.includes.forEach {
            engine.put(it.key, it.value[this])
        }
        meta.forEach {
            engine.put(it.key, it.value)
        }
        engine.put("api", plugin.abstractScriptUtils)
    }
    fun directEval(): Any? {
        return try {
            engine.eval(mergedString)
        } catch (e: ScriptException) {
            plugin.server.console.sendMessage(FormatHeader.ERROR,"脚本 §c${description.name} §7解析时出现错误, 请检查脚本格式.")
        }
    }

    fun execute(function: String = options.main, args: Array<Any?> = arrayOf()): Any? {
        /*if (engine !is Invocable)*/
        return try {
            directEval()

            val invocable = engine as Invocable

            invocable.invokeFunction(function, *args)
        } catch (e: Exception) {
            plugin.server.console.sendMessage(FormatHeader.ERROR,"脚本 §c${description.name} §7执行函数 $function 时发生错误.")
        }
    }


}