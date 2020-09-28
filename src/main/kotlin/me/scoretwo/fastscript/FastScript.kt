package me.scoretwo.fastscript

import me.scoretwo.fastscript.api.plugin.FastScriptMain
import me.scoretwo.fastscript.api.script.ScriptManager
import java.io.File

class FastScript(val dataFolder: File, private val classLoader: ClassLoader) {

    val scriptManager = ScriptManager()

    fun initInternalScripts() {

    }

    fun onReload() {
        initInternalScripts()
    }

    companion object {
        var instance: FastScript? = null
        lateinit var main: FastScriptMain

        fun setBootstrap(main: FastScriptMain) {
            if (instance != null) {
                throw UnsupportedOperationException("Cannot redefine instance")
            }

            this.main = main
            instance = FastScript(main.getDataFolder(), main.getPluginClassLoader())
        }


    }

}