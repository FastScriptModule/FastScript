package me.scoretwo.fastscript.api.script

import me.scoretwo.fastscript.api.script.custom.ConfigScriptOption

/**
 * @author Score2
 * @date 2021/2/8 13:28
 *
 * @project FastScript
 */
interface ScriptOption {

    var main: String
    var meta: MutableMap<String, Any?>

    fun toConfigOption() = try {
        this as ConfigScriptOption
    } catch (t: Throwable) {
        null
    }

    fun get(path: String): Any? = meta[path.toLowerCase()]

    fun getString(path: String): String? = meta[path.toLowerCase()]?.toString()

    fun set(path: String, value: Any?) {
        meta[path] = value
    }

    fun getBoolean(path: String) = meta[path.toLowerCase()] == true

    fun getStringList(path: String): List<String>? {
        val list = getList(path)
        val rawList = mutableListOf<String>()
        list?.forEach {
            rawList.add(it.toString())
        } ?: return null
        return rawList
    }

    fun getList(path: String): List<*>? {
        val list = meta[path]
        if (list !is List<*>) {
            return null
        }
        return list
    }

}