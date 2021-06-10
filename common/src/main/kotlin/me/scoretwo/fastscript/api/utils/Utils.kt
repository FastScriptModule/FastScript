package me.scoretwo.fastscript.utils

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.config.SettingConfig
import me.scoretwo.fastscript.languages
import me.scoretwo.fastscript.plugin
import me.scoretwo.utils.bukkit.configuration.yaml.patchs.ignoreCase
import me.scoretwo.utils.command.CommandBuilder
import me.scoretwo.utils.command.CommandNexus
import me.scoretwo.utils.sender.GlobalPlayer
import me.scoretwo.utils.sender.GlobalSender
import me.scoretwo.utils.syntaxes.FileUtils
import net.md_5.bungee.api.ChatColor
import sun.misc.Unsafe
import java.io.File
import java.io.InputStream
import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.net.URL

object Utils {

    fun saveDefaultResource(target: File, inputStream: InputStream) {
        target.parentFile.mkdirs()
        if (!target.exists()) {
            saveResource(target, inputStream)
        }
    }

    fun saveResource(target: File, inputStream: InputStream) {
        target.parentFile.mkdirs()
        FileUtils.save(target, inputStream)
    }


    // Copy in https://github.com/TabooLib/TabooLib-Loader
    val unsafe: Unsafe = let {
        val field = Unsafe::class.java.getDeclaredField("theUnsafe")
        field.isAccessible = true
        field[null] as Unsafe
    }
    // Copy in https://github.com/TabooLib/TabooLib-Loader
    val lookup: MethodHandles.Lookup = let {
        val lookupField = MethodHandles.Lookup::class.java.getDeclaredField("IMPL_LOOKUP")
        val lookupBase = unsafe.staticFieldBase(lookupField)
        val lookupOffset = unsafe.staticFieldOffset(lookupField)
        unsafe.getObject(lookupBase, lookupOffset) as MethodHandles.Lookup
    }

    // Copy in https://github.com/TabooLib/TabooLib-Loader
    fun addPath(file: File): Boolean {
        try {
            val loader: ClassLoader = FastScript.instance.plugin.pluginClassLoader
            if (loader.javaClass.simpleName == "LaunchClassLoader") {
                val methodHandle: MethodHandle = lookup.findVirtual(
                    loader.javaClass, "addURL", MethodType.methodType(
                        Void.TYPE,
                        URL::class.java
                    )
                )
                methodHandle.invoke(loader, file.toURI().toURL())
            } else {
                val ucpField: Field? = try {
                    loader.javaClass.getDeclaredField("ucp")
                } catch (e: NoSuchFieldError) {
                    loader.javaClass.superclass.getDeclaredField("ucp")
                } catch (e: NoSuchFieldException) {
                    loader.javaClass.superclass.getDeclaredField("ucp")
                }
                val ucpOffset: Long = unsafe.objectFieldOffset(ucpField)
                val ucp: Any = unsafe.getObject(loader, ucpOffset)
                val methodHandle: MethodHandle = lookup.findVirtual(
                    ucp.javaClass, "addURL", MethodType.methodType(
                        Void.TYPE,
                        URL::class.java
                    )
                )
                methodHandle.invoke(ucp, file.toURI().toURL())
            }
            return true
        } catch (t: Throwable) {
            t.printStackTrace()
        }
        return false
    }
}

class Assist {

    fun createCommandNexus(vararg alias: String) = CommandNexus(plugin, arrayOf(*alias))

    fun createCommandBuilder() = CommandBuilder()

    fun setPlaceholder(player: GlobalPlayer, text: String) = plugin.setPlaceholder(player, text)

}
lateinit var assist: Assist

fun String.subStringWithEscape(from: Int, to: Int, escapes: List<Int>): String {
    val builder = StringBuilder()
    if (escapes.isEmpty())
        return substring(from, to)
    val it = escapes.iterator()
    var currentfrom = from
    var currentto = it.next()
    while (currentto != to) {
        builder.append(currentfrom, currentto)
        currentfrom = currentto + 1
        currentto = if (it.hasNext())
            it.next()
        else
            to
    }
    if (currentfrom != currentto)
        builder.append(currentfrom, currentto)
    return builder.toString()
}

// 可能存在问题
fun String.protectedSplit(index: Char, protector: Pair<Char, Char>): ArrayList<String> {
    val list = ArrayList<String>()
    var inner = false
    var startIndex = 0
    val len = this.length
    val escapes = ArrayList<Int>()
    for (endIndex in 0 until len) {
        val c = this[endIndex]
        if (inner) {
            if (c == protector.second) {
                inner = false
                escapes.add(endIndex)
            }
        }
        else {
            when (c) {
                index -> {
                    list.add(subStringWithEscape(startIndex, endIndex, escapes))
                    escapes.clear()
                    startIndex = endIndex + 1
                }
                protector.first -> {
                    inner = true
                    escapes.add(endIndex)
                }
            }
        }
    }
    if (startIndex < len)
        list.add(subStringWithEscape(startIndex, len, escapes))
    return list
}

fun Boolean.toText() = if (this) "§a${languages["SUBSTANTIVE.ENABLED"]}" else "§c${languages["SUBSTANTIVE.DISABLED"]}"