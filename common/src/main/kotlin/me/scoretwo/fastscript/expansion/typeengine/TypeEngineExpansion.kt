package me.scoretwo.fastscript.expansion.typeengine

import me.scoretwo.fastscript.api.expansion.FastScriptExpansion
import me.scoretwo.fastscript.api.script.Script
import me.scoretwo.utils.sender.GlobalSender

abstract class TypeEngineExpansion: FastScriptExpansion() {

    private val typeEngineScripts = mutableMapOf<String, TypeEngineScript>()

    override fun convertScriptProcessor(script: Script) =
        if (script.options.otherSection.containsKey(sign)) {
            if (typeEngineScripts.containsKey(script.name))
                script.scriptProcessor[sign] = TypeEngineScriptProcessor(
                    typeEngineScripts[script.name]!!,
                    this
                )
            else
                script.scriptProcessor[sign] = TypeEngineScriptProcessor(
                    TypeEngineScript(
                        script.description,
                        TypeEngineScriptOptions(this, script.options.file, script.options.config)
                    ),
                    this
                )

            true
        } else {
            false
        }

}