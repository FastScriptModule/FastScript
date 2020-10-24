package me.scoretwo.fastscript.api.script

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.FormatHeader
import me.scoretwo.fastscript.api.script.options.ScriptOption
import me.scoretwo.fastscript.api.script.options.imports.ScriptImportType.*
import me.scoretwo.fastscript.config.SettingConfig
import me.scoretwo.fastscript.sendMessage
import me.scoretwo.fastscript.utils.StreamUtils
import me.scoretwo.fastscript.utils.Utils
import me.scoretwo.utils.configuration.file.YamlConfiguration
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import javax.script.ScriptException

abstract class Script {

    val name: String
    var scriptFile: File? = null
    var optionFile: File? = null
    var inputStream: InputStream
    val scriptOption: ScriptOption

    lateinit var scriptEngine: ScriptEngine

    constructor(file: File): this(file.name.substring(0, file.name.indexOf(".") - 1), FileInputStream(file)) {
        this.scriptFile = file
    }

    constructor(name: String, inputStream: InputStream) {
        this.inputStream = inputStream
        this.name = name

        optionFile = File(scriptFile!!.parentFile, "${name}.yml")

        if (scriptFile != null && !optionFile!!.exists()) {
            this.scriptOption = SettingConfig.instance.defaultScriptOption
        } else {
            this.scriptOption = ScriptOption.fromConfig(YamlConfiguration.loadConfiguration(optionFile!!))
        }

        onReload()
    }

    fun directEval(): String? {
        return try {
            scriptEngine.eval(StreamUtils.toString(inputStream)).toString()
        } catch (e: ScriptException) {
            e.printStackTrace()
            null
        }
    }

    fun onReload() {
        if (scriptFile == null) return
        Utils.saveDefaultResource(scriptFile!!, inputStream)
        val fileInputStream = FileInputStream(scriptFile!!)
        this.inputStream = fileInputStream
        fileInputStream.close()

        this.scriptEngine = ScriptEngineManager(FastScript.instance.classLoader).getEngineByName(scriptOption.engine)

        // 处理脚本的导入
        // 0 容错
        scriptOption.import.forEach {

            when (it.type) {
                OBJECT_INIT -> {
                    this.scriptEngine.put(
                            it.name,
                            Utils.getObjectInit(this, Utils.findClass(this, it.obj!!.clazz)!!, it.obj.args)
                    )
                }
                OBJECT -> {
                    this.scriptEngine.put(
                            it.name,
                            Utils.getObjectMethodResults(
                                    this,
                                    Utils.findClass(this, it.obj!!.clazz)!!,
                                    it.obj.args,
                                    it.met!!.name,
                                    it.met.args
                            )
                    )
                }
                STATIC -> {
                    this.scriptEngine.put(
                            it.name,
                            Utils.getStaticMethodResults(
                                    this,
                                    Utils.findClass(this, it.obj!!.clazz)!!,
                                    it.met!!.name
                            )
                    )
                }
                else -> {
                    FastScript.CONSOLE.sendMessage(FormatHeader.ERROR,"脚本 §c$name §7元素 §c${it.name} §7导入失败, 无法识别该元素的形式.")
                }
            }
        }
    }

}