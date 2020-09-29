package me.scoretwo.fastscript.config

class ScriptImport {

    companion object {

        class Object(clazz: String, args: Array<Any?>)
        class Static(clazz: String)
        class Method(name: String, args: Array<Any?>)

    }

}