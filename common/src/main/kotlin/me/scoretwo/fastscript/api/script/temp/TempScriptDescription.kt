package me.scoretwo.fastscript.api.script.temp

import me.scoretwo.fastscript.api.script.ScriptDescription
import java.util.*

/**
 * @author Score2
 * @date 2021/2/8 11:39
 *
 * @project FastScript
 */
class TempScriptDescription: ScriptDescription {
    override val name: String = "temp-${UUID.randomUUID()}"
    override val main: String = "main"
    override val version: String? = null
    override val description: String? = null
    override val authors: Array<String> = arrayOf()
}