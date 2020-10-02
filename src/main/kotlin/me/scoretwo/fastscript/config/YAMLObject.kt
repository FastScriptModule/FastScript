package me.scoretwo.fastscript.config

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import me.scoretwo.fastscript.utils.FileUtils
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class YAMLObject: JSONObject {

    val yaml = Yaml()

    private val clazz: Class<JSONObject> = javaClass
    private val mapField = clazz.getDeclaredField("map")

    lateinit var file: File

    constructor(file: File): super() {
        this.file = file
    }

    constructor(jsonObject: JSONObject): super(jsonObject)

    constructor(): super()

    fun load(inputStream: InputStream) {
        mapField.isAccessible = true
        mapField.set(this, JSON.toJSON(yaml.load(inputStream)))
    }

    fun save(file: File = this.file) {
        mapField.isAccessible = true
        FileUtils.save(file, toString())
    }

    override fun toString(): String {
        return yaml.dumpAsMap(mapField.get(this))
    }

    fun getLowerCase(key: String): Any? {
        for (key0 in keys) {
            if (key0 == null) {
                continue
            }
            if (key.toLowerCase() == key0.toLowerCase()) {
                return get(key0)
            }
        }
        return null
    }

    fun getLowerCaseString(key: String): String? {
        return when (val any = getLowerCase(key)) {
            is String -> {
                any
            }
            else -> null
        }
    }

    fun getLowerCaseBoolean(key: String): Boolean {
        return when (val any = getLowerCase(key)) {
            is Boolean -> {
                any
            }
            else -> false
        }
    }

    fun getLowerCaseYAMLObject(key: String): YAMLObject {
        return when (val any = getLowerCase(key)) {
            is YAMLObject -> {
                any
            }
            is JSONObject -> {
                YAMLObject(any)
            }
            else -> YAMLObject()
        }
    }

    fun getLowerCaseYAMLArray(key: String): YAMLArray {
        return when (val any = getLowerCase(key)) {
            is YAMLArray -> {
                any
            }
            is JSONArray -> {
                YAMLArray(any)
            }
            else -> YAMLArray()
        }
    }

    fun containsLowerCaseKey(key: String): Boolean {
        for (key0 in keys) {
            if (key0 == null) {
                continue
            }
            if (key.toLowerCase() == key0.toLowerCase()) {
                return true
            }
        }
        return false
    }



    companion object {

        fun loadConfiguration(file: File): YAMLObject {
            val yamlObject = YAMLObject(file)

            try {
                val fileInputStream = FileInputStream(file)
                yamlObject.load(fileInputStream)
                fileInputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return yamlObject
        }

        fun loadConfiguration(file: File, inputStream: InputStream): YAMLObject {
            val yamlObject = YAMLObject(file)

            yamlObject.load(inputStream)

            return yamlObject
        }

        fun saveToFile(file: File, yamlObject: YAMLObject) {
            yamlObject.save(file)
        }

        fun saveToFile(yamlObject: YAMLObject) {
            yamlObject.save()
        }

    }

}