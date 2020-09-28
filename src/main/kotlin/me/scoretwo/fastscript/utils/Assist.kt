package me.scoretwo.fastscript.utils

import me.scoretwo.fastscript.FastScript

class Assist {

    companion object {
        val instance = Assist()
    }

    fun sendMessage(sender: Any, string: String) = FastScript.main.sendMessage(sender, string)

}