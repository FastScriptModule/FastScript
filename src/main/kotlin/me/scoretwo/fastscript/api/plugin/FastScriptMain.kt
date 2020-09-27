package me.scoretwo.fastscript.api.plugin

import com.sun.xml.internal.ws.util.StreamUtils
import me.scoretwo.fastscript.utils.FileUtils
import java.io.File
import java.io.InputStream

interface FastScriptMain {

    fun getDataFolder(): File

    fun getPluginClassLoader(): ClassLoader

    fun saveDefaultResource(target: File, inputStream: InputStream) {
        if (target.exists()) {
            saveResource(target, inputStream)
        }
    }

    fun saveResource(target: File, inputStream: InputStream) {
        FileUtils.save(target, inputStream)
    }

}