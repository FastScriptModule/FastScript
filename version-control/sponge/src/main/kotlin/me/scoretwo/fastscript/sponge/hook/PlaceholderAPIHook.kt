package me.scoretwo.fastscript.sponge.hook

import me.rojo8399.placeholderapi.PlaceholderService
import me.scoretwo.fastscript.api.plugin.FastScriptPlugin

class PlaceholderAPIHook(val fastScriptPlugin: FastScriptPlugin) {


    companion object {
        lateinit var placeholderService: PlaceholderService
    }

}