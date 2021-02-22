package me.scoretwo.fastscript.api.script.temp

import me.scoretwo.fastscript.api.script.ScriptOption

/**
 * @author Score2
 * @date 2021/2/8 13:38
 *
 * @project FastScript
 */
class TempScriptOption: ScriptOption {
    override val main: String = "execute"
    override val meta: MutableMap<String, String> = mutableMapOf()
}