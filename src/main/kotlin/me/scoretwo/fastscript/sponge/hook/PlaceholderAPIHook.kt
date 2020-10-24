package me.scoretwo.fastscript.sponge.hook

import me.rojo8399.placeholderapi.PlaceholderService
import me.scoretwo.fastscript.api.plugin.FastScriptMain

class PlaceholderAPIHook(val fastScriptMain: FastScriptMain) {


    companion object {
        lateinit var placeholderService: PlaceholderService
    }

}