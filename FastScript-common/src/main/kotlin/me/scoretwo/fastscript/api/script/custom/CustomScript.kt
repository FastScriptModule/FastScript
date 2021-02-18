package me.scoretwo.fastscript.api.script.custom

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.expansion.FastScriptExpansion
import me.scoretwo.fastscript.api.script.Script
import me.scoretwo.fastscript.api.script.ScriptDescription
import me.scoretwo.fastscript.plugin
import java.io.File

open class CustomScript(
    description: ScriptDescription,
    val configOption: ConfigScriptOption,
    var scriptFiles: MutableList<File> = mutableListOf()
): Script(description, configOption) {

    open fun reload() = also {
        if (configOption.file != null && !configOption.file.exists()) {
            configOption.config.save(configOption.file)
        }
        mergeToTexts()
    }

    fun mergeToTexts() {
        texts.clear()
        FastScript.instance.expansionManager.expansions.forEach { mergeToText(it) }
    }

    fun mergeToText(expansion: FastScriptExpansion) {
        texts[expansion.sign] = StringBuilder().also {
            scriptFiles.forEach { file ->
                if (file.exists() && file.name.endsWith(".${expansion.fileSuffix}")) {
                    it.append(file.readText()).append("\n")
                }
            }
        }.toString()
        expansion.eval(this, plugin.server.console)
    }

}