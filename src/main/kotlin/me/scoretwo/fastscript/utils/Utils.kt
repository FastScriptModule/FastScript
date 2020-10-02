package me.scoretwo.fastscript.utils

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.script.Script
import java.io.File
import java.io.InputStream
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

object Utils {

    enum class MethodTypes { OBJECT, STATIC }

    /**
     * 直接对指定对象 new 来获得 instance
    */
    fun findClass(script: Script, target: String): Class<*>? {
        return try {
            Class.forName(target)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            FastScript.sendMessage(FastScript.CONSOLE,"§7[§2Fast§aScript§7] §cERROR §8| §c脚本 §4${script.name} 没有找到类 ${target}, 依赖于该类的方法将不会起作用!")
            null
        }
    }

    fun getObjectInit(script: Script, clazz: Class<*>, args: Array<Any?>): Any? {
        return try {
            val constructor = clazz.getDeclaredConstructor()
            constructor.isAccessible = true
            constructor.newInstance(args)
        } catch (e: Exception) {
            e.printStackTrace()
            FastScript.sendMessage(FastScript.CONSOLE,"§7[§2Fast§aScript§7] §cERROR §8| §7脚本 §c${script.name} §7执行初始化时发生错误, 错误如下:")
            FastScript.sendMessage(FastScript.CONSOLE, "§7${e.message}")
            null
        }
    }

    /**
     * 对指定动态对象执行某个方法并返回结果
     */
    fun getObjectMethodResults(script: Script, clazz: Class<*>, methodName: String, args: Array<Any?> = arrayOf()): Any? {
        return getObjectMethodResults(script, clazz, arrayOf(), methodName, args)
    }

    /**
     * 对指定动态对象执行某个方法并返回结果
     */
    fun getObjectMethodResults(script: Script, clazz: Class<*>, initArgs: Array<Any?>, methodName: String, args: Array<Any?> = arrayOf()): Any? {
        val method = clazz.getMethod(methodName)
        val constructor = clazz.getDeclaredConstructor()
        constructor.isAccessible = true
        return getMethodResults(script, method, constructor.newInstance(initArgs), args)
    }

    /**
     * 对指定动态对象执行某个方法并返回结果
     * 给定指定对象
     */
    fun getObjectMethodResults(script: Script, objects: Any?, methodName: String, args: Array<Any?> = arrayOf()): Any? {
        if (objects == null) return null
        val method = objects.javaClass.getMethod(methodName)
        return getMethodResults(script, method, objects, args)
    }

    /**
     * 对指定静态对象执行某个方法并返回结果
     */
    fun getStaticMethodResults(script: Script, clazz: Class<*>, methodName: String, args: Array<Any?> = arrayOf()): Any? {
        val method = clazz.getMethod(methodName)
        return getMethodResults(script, method, null, args)
    }

    /**
     * 对指定对象执行某个方法并返回结果
     */
    private fun getMethodResults(script: Script, method: Method, obj: Any?, args: Array<Any?>): Any? {
        try {
            return method.invoke(obj, args)
        } catch (e: Exception) {
            e.printStackTrace()
            FastScript.sendMessage(FastScript.CONSOLE,"§7[§2Fast§aScript§7] §cERROR §8| §7脚本 §c${script.name} §7访问方法 §c${method.name} §7时发生错误, 错误如下:")
            FastScript.sendMessage(FastScript.CONSOLE, "§7${e.message}")
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