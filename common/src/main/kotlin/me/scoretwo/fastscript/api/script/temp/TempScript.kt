package me.scoretwo.fastscript.api.script.temp

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.script.Script
import me.scoretwo.utils.sender.GlobalSender
import java.util.*

/**
 * @author Score2
 * @date 2021/2/8 11:37
 *
 * @project FastScript
 */
class TempScript(texts : MutableMap<String, String> = mutableMapOf()): Script("temp-${UUID.randomUUID()}", TempScriptOption(), texts) {

    override fun eval(sign: String, sender: GlobalSender, vararg args: String): Any? {
        for (expansion in FastScript.instance.expansionManager.expansions) {
            if (expansion.sign != sign)
                continue
            return expansion.eval(texts[sign] ?: "", sender, arrayOf(*args))
        }
        return null
    }

    override fun execute(sign: String, sender: GlobalSender, main: String, args: Array<Any?>): Any? {
        for (expansion in FastScript.instance.expansionManager.expansions) {
            if (expansion.sign != sign)
                continue
            return expansion.execute(texts[sign] ?: "", sender, main, args)
        }
        return null
    }
}