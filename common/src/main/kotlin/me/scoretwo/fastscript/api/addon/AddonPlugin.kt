package me.scoretwo.fastscript.api.addon

import me.scoretwo.fastscript.scripts

abstract class AddonPlugin: Addon {

    init {

    }

    abstract fun ionizing()

    abstract fun disable()



}