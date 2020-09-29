package me.scoretwo.fastscript.config

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import me.scoretwo.fastscript.utils.FileUtils
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class YAMLObject(val file: File): JSONObject() {

    val yaml = Yaml()

    private val clazz: Class<JSONObject> = javaClass
    private val mapField = clazz.getDeclaredField("map")

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