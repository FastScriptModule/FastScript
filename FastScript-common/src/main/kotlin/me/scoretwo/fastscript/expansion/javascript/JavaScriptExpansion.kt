package me.scoretwo.fastscript.expansion.javascript

import me.scoretwo.fastscript.expansion.typeengine.TypeEngineExpansion
import javax.script.ScriptEngine

class JavaScriptExpansion: TypeEngineExpansion() {
    override val name = "JavaScript"
    override val sign = "nashorn"
    override val fileSuffix = "js"
    override val engine = scriptEngineManager.getEngineByName("js")
}