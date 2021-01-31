package me.scoretwo.fastscript.api.script

import me.scoretwo.fastscript.FastScript
import me.scoretwo.utils.sender.GlobalSender
import java.io.File

open class Script(
    val description: ScriptDescription,
    val options: ScriptOptions,
    val scriptFiles: MutableList<File> = mutableListOf()
) {

    val name = description.name

    // sign, processor
    val scriptProcessor = mutableMapOf<String, ScriptProcessor>()
    // sign, mergedText
    val mergedTexts = mutableMapOf<String, StringBuilder>()

    open fun eval(sign: String, sender: GlobalSender): Any? {
        if (!scriptProcessor.containsKey(sign)) {
            return null
        }
        return scriptProcessor[sign]!!.eval(sender)
    }
    open fun execute(sign: String, sender: GlobalSender, main: String = options.main, args: Array<Any?> = arrayOf()): Any? {
        if (!scriptProcessor.containsKey(sign)) {
            return null
        }
        return scriptProcessor[sign]!!.execute(sender, main, args)
    }

    open fun reload() {
        if (!options.file.exists()) {
            options.config.save(options.file)
        }
    }

    fun mergeToTexts() {
        mergedTexts.clear()
        FastScript.instance.expansionManager.expansions.forEach {
            mergeToText(it.sign)
        }
    }

    fun mergeToText(sign: String) {
        mergedTexts[sign] = StringBuilder().also {
            scriptFiles.forEach { file ->
                if (file.exists() && file.name.endsWith(".$sign")) {
                    it.append(file.readText()).append("\n")
                }
            } }

    }

}