package me.scoretwo.fastscript.api.script

import me.scoretwo.fastscript.api.expansion.FastScriptExpansion
import java.io.File

open class FileScript(
    expansion: FastScriptExpansion,
    description: ScriptDescription,
    val configOptions: ConfigScriptOptions,
    val files: MutableList<File> = mutableListOf()
): AbstractScript(expansion, description, configOptions) {

    init {
        reload()
    }

    fun addFile(file: File) {
        files.add(file)
        reload()
    }

    fun removeFile(file: File) {
        files.remove(file)
    }

    open fun reload() {
        if (!configOptions.file.exists()) {
            configOptions.config.save(configOptions.file)
        }

        files.forEachIndexed { i, file -> textScripts[i] = file.readText() }

    }

}