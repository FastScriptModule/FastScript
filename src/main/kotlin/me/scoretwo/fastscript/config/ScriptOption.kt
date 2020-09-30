package me.scoretwo.fastscript.config

class ScriptOption {


    companion object {

        interface HasMethod

        class Object(clazz: String, args: Array<Any?>): HasMethod
        class Static(clazz: String)
        class Method(name: String, args: Array<Any?>)

    }
}