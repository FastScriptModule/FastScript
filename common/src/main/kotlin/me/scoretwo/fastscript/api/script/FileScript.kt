package me.scoretwo.fastscript.api.script

import java.io.File

open class FileScript(
    description: ScriptDescription,
    private val options: ConfigScriptOptions,
    val files: MutableList<File> = mutableListOf()
): AbstractScript(description, options) {

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
        if (!options.file.exists()) {
            options.config.save(options.file)
        }

        files.forEachIndexed { i, file -> textScripts[i] = file.readText() }

    }

}