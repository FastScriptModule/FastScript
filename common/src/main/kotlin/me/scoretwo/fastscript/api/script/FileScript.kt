package me.scoretwo.fastscript.api.script

import java.io.File

open class FileScript(description: ScriptDescription, val files: MutableList<File>): AbstractScript(description) {

    init {
        reload()
    }

    open fun reload() = files.forEachIndexed { i, file -> textScripts[i] = file.readText() }

}