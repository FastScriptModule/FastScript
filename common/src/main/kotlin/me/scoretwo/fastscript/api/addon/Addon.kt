package me.scoretwo.fastscript.api.addon

import me.scoretwo.fastscript.api.script.AbstractScript

interface Addon {

    val name: String
    val sign: String

    fun processScript(script: AbstractScript)

    fun executeScript(script: AbstractScript)

}