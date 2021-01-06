package me.scoretwo.fastscript.utils

import java.io.ByteArrayOutputStream
import java.io.InputStream

object StreamUtils {

    fun toString(inputStream: InputStream): String {
        val result = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } != -1) {
            result.write(buffer, 0, length)
        }
        return result.toString("UTF-8")
    }

}