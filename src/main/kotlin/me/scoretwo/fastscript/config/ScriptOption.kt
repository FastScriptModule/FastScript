package me.scoretwo.fastscript.config

class ScriptOption {


    companion object {

        enum class TYPE { INIT, OBJECT, STATIC }

        class Static(clazz: String)
        class Object(clazz: String, args: Array<Any?>)

        class Method(name: String, args: Array<Any?>)

    }
}