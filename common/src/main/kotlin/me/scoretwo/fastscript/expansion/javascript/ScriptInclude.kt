package me.scoretwo.fastscript.expansion.javascript

import me.scoretwo.fastscript.expansion.javascript.ScriptIncludeType.*
import me.scoretwo.fastscript.expansion.javascript.exception.IncludeFormatException
import me.scoretwo.fastscript.expansion.javascript.exception.TypeInferenceException
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.plugin
import me.scoretwo.fastscript.sendMessage
import me.scoretwo.utils.bukkit.configuration.yaml.ConfigurationSection
import me.scoretwo.utils.bukkit.configuration.yaml.patchs.getLowerCaseNode
import java.lang.reflect.Method

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

    operator fun get(script: JavaScript): Any? {
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
                    e.printStackTrace()
                    plugin.server.console.sendMessage(FormatHeader.ERROR, "脚本 §c${script.description.name} §7执行初始化时发生错误, 错误如下:")
                    plugin.server.console.sendMessage("§7${e.message}")
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


    fun findClass(script: JavaScript, target: String) = try {
        Class.forName(target)
    } catch (e: ClassNotFoundException) {
        e.printStackTrace()
        plugin.server.console.sendMessage(FormatHeader.ERROR, "脚本 §c${script.description.name} §7没有找到类 §c${target}§7, 错误如下:")
        plugin.server.console.sendMessage("§7${e.message}")
        null
    }

    fun accessMethod(script: JavaScript, `object`: Any?, method: Method) = try {
        method.invoke(`object`, obj!!.second)
    } catch (e: Exception) {
        e.printStackTrace()
        plugin.server.console.sendMessage(FormatHeader.ERROR, "脚本 §c${script.description.name} §7访问方法 §c${method.name} §7时发生错误, 错误如下:")
        plugin.server.console.sendMessage("§7${e.message}")
        null
    }

    companion object {

        fun fromSection(section: ConfigurationSection): ScriptInclude {
            val type = ScriptIncludeType.valueOf(section.getString(section.getLowerCaseNode("type")))

            val obj: Pair<String, List<Any?>?>?
            section.getConfigurationSection(section.getLowerCaseNode("object")).also {
                obj = Pair(it.getString(it.getLowerCaseNode("class")), it.getList(it.getLowerCaseNode("args")))
            }
            val met: Pair<String, List<Any?>?>?
            section.getConfigurationSection(section.getLowerCaseNode("method")).also {
                met = Pair(it.getString(it.getLowerCaseNode("name")), it.getList(it.getLowerCaseNode("args")))
            }
            return ScriptInclude(type, obj, met)
        }

    }

}