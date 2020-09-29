package me.scoretwo.fastscript.api.script

class ScriptManager {

    val scripts = mutableListOf<Script>()

    fun getScript(name: String): Script? {
        for (script in scripts) {
            if (script.name == name) return script
        }
        return null
    }

}