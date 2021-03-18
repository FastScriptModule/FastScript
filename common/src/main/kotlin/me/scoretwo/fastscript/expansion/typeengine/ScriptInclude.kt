package me.scoretwo.fastscript.expansion.typeengine

import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.api.script.custom.CustomScript
import me.scoretwo.fastscript.expansion.typeengine.exception.IncludeFormatException
import me.scoretwo.fastscript.expansion.typeengine.exception.TypeInferenceException
import me.scoretwo.fastscript.plugin
import me.scoretwo.fastscript.sendMessage
import me.scoretwo.fastscript.expansion.typeengine.ScriptIncludeType.*
import me.scoretwo.utils.bukkit.configuration.yaml.ConfigurationSection
import me.scoretwo.utils.bukkit.configuration.yaml.patchs.ignoreCase
import java.lang.reflect.Method

@Deprecated("已弃用该类, 现有更好的方法代替它: js: Java.type(\"xxxx.xxxx\")")
class ScriptInclude(
    type: ScriptIncludeType?,
    val obj: Pair<String, List<Any?>?>?,
    val met: Pair<String, List<Any?>?>?
) {

    val type: ScriptIncludeType

    init {
        if (type == null) {
            if (hasObject() && hasObjectArgs())
                this.type = INIT
            else if (hasObject() && hasObjectArgs() && hasMethod())
                this.type = OBJECT
            else if (hasObject() && hasMethod())
                this.type = STATIC
            else
                throw TypeInferenceException("Don't to inference script's type")
        } else this.type = type
    }

    operator fun get(script: CustomScript): Any? {
        obj ?: throw IncludeFormatException()
        met ?: throw IncludeFormatException()
        val clazz = findClass(script, obj.first) ?: throw ClassNotFoundException()
        when (type) {
            OBJECT -> {
                val method = clazz.getMethod(met.first)
                val constructor = clazz.getDeclaredConstructor()
                constructor.isAccessible = true
                return accessMethod(script, clazz.newInstance(), method)
            }
            INIT -> {
                return try {
                    val constructor = clazz.getDeclaredConstructor()
                    constructor.isAccessible = true
                    constructor.newInstance(*obj.second!!.toTypedArray())
                } catch (e: Exception) {
                    plugin.server.console.sendMessage(FormatHeader.ERROR, "脚本 §c${script.name} §7执行初始化时发生错误, 错误如下:\n§8${e.stackTraceToString()}")
                    null
                }
            }
            STATIC -> {
                val method = clazz.getMethod(met.first)
                return accessMethod(script, null, method)
            }
        }
    }

    fun hasObject() = obj != null
    fun hasObjectArgs() = if (hasObject()) obj!!.second != null else false

    fun hasMethod() = met != null
    fun hasMethodArgs() = if (hasMethod()) met!!.second != null else false


    fun findClass(script: CustomScript, target: String) = try {
        Class.forName(target)
    } catch (e: ClassNotFoundException) {
        plugin.server.console.sendMessage(FormatHeader.ERROR, "脚本 §c${script.name} §7没有找到类 §c${target}§7, 错误如下:\n§8${e.stackTraceToString()}")
        null
    }

    fun accessMethod(script: CustomScript, `object`: Any?, method: Method) = try {
        method.invoke(`object`, obj!!.second)
    } catch (e: Exception) {
        plugin.server.console.sendMessage(FormatHeader.ERROR, "脚本 §c${script.name} §7访问方法 §c${method.name} §7时发生错误, 错误如下:\n§8${e.stackTraceToString()}")
        null
    }

    companion object {

        fun fromSection(section: ConfigurationSection): ScriptInclude {
            val type = ScriptIncludeType.valueOf(section.getString(section.ignoreCase("type")))

            val obj: Pair<String, List<Any?>?>?
            section.getConfigurationSection(section.ignoreCase("object")).also {
                obj = Pair(it.getString(it.ignoreCase("class")), it.getList(it.ignoreCase("args")))
            }
            val met: Pair<String, List<Any?>?>?
            section.getConfigurationSection(section.ignoreCase("method")).also {
                met = Pair(it.getString(it.ignoreCase("name")), it.getList(it.ignoreCase("args")))
            }
            return ScriptInclude(type, obj, met)
        }

    }

}