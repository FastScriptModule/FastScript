package me.scoretwo.fastscript.api.utils.maven

import sun.misc.Unsafe;
import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.utils.process.ProcessResult
import me.scoretwo.fastscript.api.utils.process.ProcessResultType
import me.scoretwo.fastscript.plugin
import me.scoretwo.fastscript.utils.Utils
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLClassLoader


/**
 * @author Score2
 * @date 2021/2/25 19:05
 *
 * @project FastScript
 */
class MavenArtifact {

    val groupId: String
    val artifactId: String
    val version: String

    constructor(groupId: String, artifactId: String, version: String, vararg repositories: String) {
        this.groupId = groupId
        this.artifactId = artifactId
        this.version = version
        this.repositories = arrayOf(*repositories, "https://maven.aliyun.com/nexus/content/groups/public/")
    }

    constructor(combined: String, vararg repositories: String) {
        val split = combined.split(":")
        this.groupId = split[0]
        this.artifactId = split[1]
        this.version = split[2]
        this.repositories = arrayOf(*repositories, "https://maven.aliyun.com/nexus/content/groups/public/")
    }

    val repositories: Array<String>

    fun toURL(repository: String) = StringBuilder(repository).also {
        if (repository.last() != '/') it.append("/")
        it.append("${groupId.replace(".", "/")}/")
        it.append("$artifactId/")
        it.append("$version/")
        it.append("$artifactId-$version.jar")
    }.toString()

    fun download(): ProcessResult {
        repositories.forEach {
            if (downLoad(it).type == ProcessResultType.FAILED) return@forEach
            return ProcessResult(ProcessResultType.SUCCESS)
        }
        return ProcessResult(ProcessResultType.FAILED)
    }

    private fun downLoad(repository: String): ProcessResult {
        try {
            val url = toURL(repository)
            val folder = File(plugin.dataFolder, "lib")
            val file = File(folder, "$artifactId-$version.jar")
            if (file.exists()) {
                return ProcessResult(ProcessResultType.OTHER, "exists")
            }

            val httpURLConnection = (URL(url).openConnection() as HttpURLConnection).also {
                it.connectTimeout = 20 * 1000
                it.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)")
            }

            val inputStream: InputStream = httpURLConnection.inputStream
            val bytes: ByteArray = inputStream.readBytes()

            if (!folder.exists()) {
                folder.mkdir()
            }

            val fileOutputStream = FileOutputStream(file)
            fileOutputStream.write(bytes)
            fileOutputStream.close()
            inputStream.close()

            Utils.addPath(file)
            /*val method = URLClassLoader::class.java.getDeclaredMethod("addURL", URL::class.java)
            method.isAccessible = true
            method.invoke(plugin.pluginClassLoader, file.toURI().toURL())*/
            return ProcessResult(ProcessResultType.SUCCESS)
        } catch (t: Throwable) {
            t.printStackTrace()
            return ProcessResult(ProcessResultType.FAILED)
        }
    }


    override fun toString() = "$groupId:$artifactId:$version"

}