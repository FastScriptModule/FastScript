package me.scoretwo.fastscript.api.script

import com.alibaba.fastjson.JSONObject
import me.scoretwo.fastscript.api.yaml.YAMLObject

class ScriptImport(val name: String, val type: TYPE, val obj: Object?, val met: Method?) {



    companion object {
        enum class TYPE { INIT, OBJECT, STATIC, IMPORT, ERROR }
        class Object(val clazz: String, val args: Array<Any?> = arrayOf())
        class Method(val name: String, val args: Array<Any?> = arrayOf())


        fun fromConfigSection(name: String, jsonObject: JSONObject): ScriptImport {
            return fromConfigSection(name, jsonObject)
        }

        fun fromConfigSection(name: String, yamlObject: YAMLObject): ScriptImport {
            val obj: Object?
            val met: Method?
            val type = when {
                yamlObject.containsLowerCaseKey("init") -> {
                    val init = yamlObject.getLowerCaseYAMLObject("init")
                    obj = Object(init.getLowerCaseString("class")!!)
                    met = null
                    TYPE.INIT
                }
                yamlObject.containsLowerCaseKey("object") -> {
                    val object0 = yamlObject.getLowerCaseYAMLObject("object")
                    val method = yamlObject.getLowerCaseYAMLObject("method")
                    obj = Object(object0.getLowerCaseString("class")!!, object0.getLowerCaseYAMLArray("args").toArray())
                    met = Method(method.getLowerCaseString("name")!!, method.getLowerCaseYAMLArray("args").toArray())
                    TYPE.OBJECT
                }
                yamlObject.containsLowerCaseKey("static") -> {
                    val static = yamlObject.getLowerCaseYAMLObject("static")
                    val method = yamlObject.getLowerCaseYAMLObject("method")
                    obj = Object(static.getLowerCaseString("class")!!)
                    met = Method(method.getLowerCaseString("name")!!, method.getLowerCaseYAMLArray("args").toArray())
                    TYPE.STATIC
                }
                /*
                暂不支持
                jsonObject.containsKey("import") -> {
                    TYPE.IMPORT
                }
                */
                else -> {
                    obj = null
                    met = null
                    TYPE.ERROR
                }
            }


            return ScriptImport(name, type, obj, met)
        }

    }

}