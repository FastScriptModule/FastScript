package me.scoretwo.fastscript.api.script

import java.io.File

abstract class AbstractScript (
    val description: ScriptDescription,
    options: ScriptOptions,
    val textScripts: MutableList<String> = mutableListOf("")

) {
    val meta = mutableMapOf<String, Any?>()


}