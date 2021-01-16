package me.scoretwo.fastscript.addon.javascript

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.script.ConfigScriptOptions
import me.scoretwo.fastscript.api.script.FileScript
import me.scoretwo.fastscript.api.script.ScriptDescription
import me.scoretwo.fastscript.plugin
import java.io.File
import javax.script.ScriptEngineManager

class JavaScript(
    description: ScriptDescription,
    val options: JavaScriptOptions,
    files: MutableList<File>
): FileScript(description, options, files) {

    val engine = ScriptEngineManager(plugin.pluginClassLoader).getEngineByName(options.engine)

    init {

    }



}