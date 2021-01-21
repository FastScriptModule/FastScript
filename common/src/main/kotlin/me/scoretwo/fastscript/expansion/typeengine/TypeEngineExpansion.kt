package me.scoretwo.fastscript.expansion.typeengine

import me.scoretwo.fastscript.api.expansion.FastScriptExpansion
import me.scoretwo.fastscript.api.script.Script
import me.scoretwo.utils.sender.GlobalSender

abstract class TypeEngineExpansion: FastScriptExpansion() {

    override fun convertScriptProcessor(script: Script) =
        if (script.options.otherSection.containsKey(sign)) {
            script.scriptProcessor[sign] = TypeEngineScriptProcessor(script, this)
            true
        } else {
            false
        }

}