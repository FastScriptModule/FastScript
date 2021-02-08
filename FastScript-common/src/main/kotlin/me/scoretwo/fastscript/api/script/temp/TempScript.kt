package me.scoretwo.fastscript.api.script.temp

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.script.Script
import me.scoretwo.utils.sender.GlobalSender

/**
 * @author Score2
 * @date 2021/2/8 11:37
 *
 * @project FastScript
 */
class TempScript(texts : MutableMap<String, String> = mutableMapOf()): Script(TempScriptDescription(), TempScriptOption(), texts) {

    override fun eval(sign: String, sender: GlobalSender): Any? {
        for (expansion in FastScript.instance.expansionManager.expansions) {
            if (expansion.sign != sign)
                continue
            return expansion.eval(texts[sign] ?: "", sender)
        }
        return null
    }

    override fun execute(sign: String, sender: GlobalSender, main: String = option.main, args: Array<Any?> = arrayOf()): Any? {
        for (expansion in FastScript.instance.expansionManager.expansions) {
            if (expansion.sign != sign)
                continue
            return expansion.execute(texts[sign] ?: "", sender, main, args)
        }
        return null
    }
}