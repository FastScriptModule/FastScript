package me.scoretwo.fastscript.utils

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.config.SettingConfig
import me.scoretwo.fastscript.plugin
import me.scoretwo.utils.bukkit.configuration.yaml.patchs.getLowerCaseNode
import me.scoretwo.utils.command.CommandBuilder
import me.scoretwo.utils.command.CommandNexus
import me.scoretwo.utils.sender.GlobalPlayer
import me.scoretwo.utils.sender.GlobalSender
import me.scoretwo.utils.syntaxes.FileUtils
import net.md_5.bungee.api.ChatColor
import java.io.File
import java.io.InputStream
import java.lang.reflect.Method

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

}

class Assist {

    fun createCommandNexus(vararg alias: String) = CommandNexus(plugin, arrayOf(*alias))

    fun createCommandBuilder() = CommandBuilder()

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
