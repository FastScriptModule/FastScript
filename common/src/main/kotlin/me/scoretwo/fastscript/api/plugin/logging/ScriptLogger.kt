package me.scoretwo.fastscript.api.plugin.logging

interface ScriptLogger {

    fun info(msg: String)

    fun warn(msg: String)
    fun warn(msg: String, t: Throwable)

    fun error(msg: String)
    fun error(msg: String, t: Throwable)

}