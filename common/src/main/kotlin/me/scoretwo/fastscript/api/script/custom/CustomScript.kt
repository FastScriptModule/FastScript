package me.scoretwo.fastscript.api.script.custom

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.expansion.FastScriptExpansion
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.api.script.Script
import me.scoretwo.fastscript.api.script.ScriptDescription
import me.scoretwo.fastscript.plugin
import me.scoretwo.fastscript.sendMessage
import me.scoretwo.utils.server.globalServer
import me.scoretwo.utils.server.task.TaskType
import java.io.File

open class CustomScript(
    name: String,
    val configOption: ConfigScriptOption,
    var scriptFiles: MutableList<File> = mutableListOf()
): Script(name, configOption) {

    open fun reload() = also {
        if (configOption.file != null && !configOption.file.exists()) {
            globalServer.schedule.task(plugin, TaskType.ASYNC, Runnable {
                configOption.config.save(configOption.file)
            })
        }
        mergeToTexts()
    }

    fun mergeToTexts() {
        if (init.useAsync) {
            globalServer.schedule.task(plugin, TaskType.ASYNC, Runnable {
                texts.clear()
                FastScript.instance.expansionManager.expansions.forEach { mergeToText(it) }
                plugin.server.console.sendMessage(FormatHeader.DEBUG, "script §b$name §6async §7loaded!")
            })
            return
        }

        texts.clear()
        FastScript.instance.expansionManager.expansions.forEach { mergeToText(it) }
    }

    fun mergeToText(expansion: FastScriptExpansion) {
        if (let {
                scriptFiles.forEach { if (it.name.endsWith(expansion.fileSuffix, true)) { return@let false } }
                return@let true
            })
                return

        texts[expansion.sign] = StringBuilder().also {
            scriptFiles.forEach { file ->
                if (file.exists() && file.name.endsWith(".${expansion.fileSuffix}")) {
                    it.append(file.readText()).append("\n")
                }
            }
        }.toString()
        expansion.eval(this, plugin.server.console)
        expansion.execute(this, plugin.server.console, "init")
    }

}