package me.scoretwo.fastscript.utils

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.config.SettingConfig
import me.scoretwo.fastscript.plugin
import me.scoretwo.utils.bukkit.configuration.yaml.patchs.getLowerCaseNode
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
