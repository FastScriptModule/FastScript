package me.scoretwo.fastscript.api.script

import me.scoretwo.fastscript.api.expansion.FastScriptExpansion
import java.io.File

abstract class AbstractScript (
    val expansion: FastScriptExpansion,
    val description: ScriptDescription,
    val options: ScriptOptions,
    val textScripts: MutableList<String> = mutableListOf("")

) {
    val meta = mutableMapOf<String, Any?>()


}