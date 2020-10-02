package me.scoretwo.fastscript.api.script

import com.alibaba.fastjson.JSONObject
import me.scoretwo.fastscript.api.yaml.YAMLObject

class ScriptOption(val engine: String, val import: MutableList<ScriptImport>, val main: String? = null) {

    companion object {

        fun fromConfigSection(yamlObject: YAMLObject): ScriptOption {
            val engine = yamlObject.getLowerCaseString("engine")!!
            val main = yamlObject.getLowerCaseString("main")!!

            val import = mutableListOf<ScriptImport>()

            for (i in yamlObject.getLowerCaseYAMLObject("import")) {

                import.add(ScriptImport.fromConfigSection(i.key, i.value as JSONObject))

            }

            return ScriptOption(engine, import, main)
        }


    }

}