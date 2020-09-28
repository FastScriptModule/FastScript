package me.scoretwo.fastscript.utils

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.script.Script
import java.io.File
import java.io.InputStream
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

object Utils {

    enum class MethodTypes { OBJECT, STATIC }
    class Empty { companion object { val instance = Empty() } }

    /**
     * 直接对指定对象 new 来获得 instance
    */
    fun findClass(script: Script, target: String): Class<*> {
        return try {
            Class.forName(target)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            FastScript.main.sendConsoleMessage("&7[&2Fast&aScript&7] &cERROR &8| &c脚本 &4${script.name} 没有找到类 ${target}, 依赖于该类的方法将不会起作用!")
            Empty.instance.javaClass
        }
    }

    fun getObjectMethodResults(script: Script, clazz: Class<*>, methodName: String, args: Array<Any?> = arrayOf()): Any? {
        val method = clazz.getMethod(methodName)
        return getMethodResults(script, method, clazz.newInstance(), args)
    }

    fun getStaticMethodResults(script: Script, clazz: Class<*>, methodName: String, args: Array<Any?> = arrayOf()): Any? {
        val method = clazz.getMethod(methodName)
        return getMethodResults(script, method, null, args)
    }

    private fun getMethodResults(script: Script, method: Method, obj: Any?, args: Array<Any?>): Any? {
        try {
            return method.invoke(obj, args)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            FastScript.main.sendConsoleMessage("&7[&2Fast&aScript&7] &cERROR &8| &c脚本 &4${script.name} 访问方法 ${method.name} 时发生错误, 原因: 非法访问 private 或 protected 方法!")
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            FastScript.main.sendConsoleMessage("&7[&2Fast&aScript&7] &cERROR &8| &c脚本 &4${script.name} 访问方法 ${method.name} 时发生错误, 原因: 某个参数不正确!")
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
            FastScript.main.sendConsoleMessage("&7[&2Fast&aScript&7] &cERROR &8| &c脚本 &4${script.name} 访问方法 ${method.name} 时发生错误, 原因: 调用方法时发生内部错误!")
        }
        return null
    }


    fun saveDefaultResource(target: File, inputStream: InputStream) {
        if (target.exists()) {
            saveResource(target, inputStream)
        }
    }

    fun saveResource(target: File, inputStream: InputStream) {
        FileUtils.save(target, inputStream)
    }

}