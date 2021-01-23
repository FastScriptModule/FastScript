package me.scoretwo.fastscript.expansion.kotlinscript

import me.scoretwo.fastscript.expansion.typeengine.TypeEngineExpansion

class KotlinScriptExpansion: TypeEngineExpansion() {
    override val name = "KotlinScript"
    override val sign = "kts"
    override val fileSuffix = "kts"
}