package me.scoretwo.fastscript.api.script

import me.scoretwo.fastscript.utils.Utils
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

abstract class Script {

    val name: String
    var file: File? = null
    var inputStream: InputStream

    constructor(file: File): this(file.name.substring(0, file.name.indexOf(".") - 1), FileInputStream(file)) {
        this.file = file
    }

    constructor(name: String, inputStream: InputStream) {
        this.inputStream = inputStream
        this.name = name
    }

    fun onReload() {
        if (file == null) return
        Utils.saveDefaultResource(file!!, inputStream)
        this.inputStream = FileInputStream(file!!)
    }

}