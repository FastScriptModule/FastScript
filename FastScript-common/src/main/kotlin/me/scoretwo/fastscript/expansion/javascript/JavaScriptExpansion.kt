package me.scoretwo.fastscript.expansion.javascript

import me.scoretwo.fastscript.expansion.typeengine.TypeEngineExpansion

class JavaScriptExpansion: TypeEngineExpansion() {
    override val name: String = "JavaScript"
    override val sign: String = name.toLowerCase()
    override val fileSuffix: String = "js"
}