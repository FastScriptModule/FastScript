package me.scoretwo.fastscript.expansion.scala

import me.scoretwo.fastscript.expansion.typeengine.TypeEngineExpansion
import javax.script.ScriptEngine

/**
 * @author Score2
 * @date 2021/2/26 2:29
 *
 * @project FastScript
 */
class ScalaExpansion: TypeEngineExpansion() {
    override val name = "Scala"
    override val sign = "scala"
    override val fileSuffix = "scala"
    override val engine: ScriptEngine = scriptEngineManager.getEngineByName("scala") ?: scriptEngineManager.getEngineByName("scala")
}