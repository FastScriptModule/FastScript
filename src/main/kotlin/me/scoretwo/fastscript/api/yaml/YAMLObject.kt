package me.scoretwo.fastscript.api.yaml

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import me.scoretwo.fastscript.utils.FileUtils
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

open class YAMLObject: JSONObject {

    val yaml = Yaml()

    private val clazz: Class<JSONObject> = javaClass
    private val mapField = clazz.getDeclaredField("map")

    lateinit var file: File

    constructor(file: File): super() {
        this.file = file
    }

    constructor(jsonObject: JSONObject): super(jsonObject)

    constructor(): super()

    fun setMap(mutableMap: JSONObject) {
        mapField.isAccessible = true
        mapField.set(this, mutableMap)
    }

    fun getMap(): JSONObject {
        mapField.isAccessible = true
        return mapField.get(this) as JSONObject
    }

    open fun load(inputStream: InputStream) {
        setMap(JSON.toJSON(yaml.load(inputStream)) as JSONObject)
    }

    fun save(file: File = this.file) {
        FileUtils.save(file, toString())
    }

    override fun toString(): String {
        return yaml.dumpAsMap(getMap())
    }

    fun getLowerCaseKeyName(key: String): String? {
        for (key0 in keys) {
            if (key0 == null) {
                continue
            }
            if (key.toLowerCase() == key0.toLowerCase()) {
                return key0
            }
        }
        return null
    }

    fun getLowerCase(key: String): Any? {
        return get(getLowerCaseKeyName(key))
    }

    fun getLowerCaseString(key: String): String? {
        return getString(getLowerCaseKeyName(key))
    }

    fun getLowerCaseBoolean(key: String): Boolean {
        return getBoolean(getLowerCaseKeyName(key))
    }

    fun getLowerCaseInt(key: String): Int {
        return getInteger(getLowerCaseKeyName(key))
    }

    fun getLowerCaseDouble(key: String): Double {
        return getDouble(getLowerCaseKeyName(key))
    }

    fun getLowerCaseFloat(key: String): Float {
        return getFloat(getLowerCaseKeyName(key))
    }

    fun getLowerCaseShort(key: String): Short {
        return getShort(getLowerCaseKeyName(key))
    }

    fun getLowerCaseYAMLObject(key: String): YAMLObject {
        return YAMLObject(getJSONObject(getLowerCaseKeyName(key)))
    }

    fun getLowerCaseYAMLArray(key: String): YAMLArray {
        return YAMLArray(getJSONArray(getLowerCaseKeyName(key)))
    }

    fun containsLowerCaseKey(key: String): Boolean {
        return getLowerCaseKeyName(key) != null
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