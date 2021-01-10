package me.scoretwo.fastscript.sponge.hook

import me.rojo8399.placeholderapi.PlaceholderService
import me.scoretwo.fastscript.api.plugin.ScriptPlugin

class PlaceholderAPIHook(val scriptPlugin: ScriptPlugin) {


    companion object {
        lateinit var placeholderService: PlaceholderService
    }

}