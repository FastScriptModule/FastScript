package me.scoretwo.fastscript.placeholder

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.setPlaceholder
import me.scoretwo.fastscript.utils.protectedSplit
import me.scoretwo.utils.sender.GlobalPlayer

object Placeholders {

    fun parse(player: GlobalPlayer, params: String): String {
        if (params.isBlank()) return ""
        val args = params.replace("@", "%").setPlaceholder(player).protectedSplit('_', Pair('[', ']'))
        if (args.isEmpty()) return ""

        when (args[0].toLowerCase()) {
            // %fastscript_script_[name]_evaluate(eval)_expansion_<args...>%
            // %fastscript_script_[name]_execute(run)_expansion_<main>_<args...>%
            "script" -> {
                when {
                    args.size < 2 -> return "Missing statement: script_name"
                    args.size < 3 -> return "Missing statement: script_action(evaluate[eval]/execute[run])"
                    args.size < 4 -> return "Missing statement: expansion_sign or expansion_name"
                }
                val script = FastScript.instance.scriptManager.getScript(args[1])
                    ?: return "Not found script: ${args[1]}"
                val expansion = FastScript.instance.expansionManager.getExpansionBySign(args[3]) ?: FastScript.instance.expansionManager.getExpansionByName(args[3])
                    ?: return "Not found expansion sign: ${args[3]}"
                when (args[2]) {
                    "eval", "evaluate" -> {
                        val args0 = if (args.size >= 4) args.slice(4 until args.size).toTypedArray() else arrayOf()
                        expansion.eval(script, player, *args0)
                    }
                    "run", "execute" -> {
                        val main = if (args.size >= 4) args[4] else script.option.main
                        val args0: Array<Any?> = if (args.size >= 5) args.slice(5 until args.size).toTypedArray() else arrayOf()
                        expansion.execute(script, player, main, args0)
                    }
                    else -> {
                        return "Not found action: ${args[2]}"
                    }
                }

            }
            // %fastscript_expansion_[sign or name]_evaluate(eval)_<text...>%
            "expansion" -> {
                when {
                    args.size < 2 -> return "Missing statement: sign or name"
                    args.size < 3 -> return "Missing statement: expansion_action(evaluate[eval])"
                }
                val expansion = FastScript.instance.expansionManager.getExpansionBySign(args[1]) ?: FastScript.instance.expansionManager.getExpansionByName(args[1])
                    ?: return "Not found expansion sign: ${args[1]}"

                when (args[2]) {
                    "evaluate", "eval" -> {
                        val text = if (args.size >= 3) args.slice(3 until args.size).joinToString("_") else ""
                        expansion.eval(text, player)
                    }
                    else -> {
                        return "Not found action: ${args[2]}"
                    }
                }

            }
        }

        return ""
    }

}