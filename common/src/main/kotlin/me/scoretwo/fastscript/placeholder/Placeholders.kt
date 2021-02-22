package me.scoretwo.fastscript.placeholder

import me.scoretwo.utils.sender.GlobalPlayer

object Placeholders {

    // %fastscript_script_[name]_evaluate(eval)_expansion%
    // %fastscript_script_[name]_execute(run)_expansion_<main>_<args...>%
    fun parse(player: GlobalPlayer, params: String): String {
        var skipIndex = -1
        val args = mutableListOf<String>().also { args ->
            val split = params.split("_")
            split.forEachIndexed { index, string ->
                if (string.startsWith("[")) {
                    split.forEachIndexed { index, s ->

                    }
                }
            }

        }


        TODO()
    }

}