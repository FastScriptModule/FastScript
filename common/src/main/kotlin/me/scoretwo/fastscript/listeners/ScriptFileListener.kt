package me.scoretwo.fastscript.listeners

import me.scoretwo.fastscript.*
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.utils.server.task.TaskType
import org.apache.commons.io.monitor.FileAlterationListener
import org.apache.commons.io.monitor.FileAlterationMonitor
import org.apache.commons.io.monitor.FileAlterationObserver
import java.io.File


class ScriptFileListener: FileAlterationListener {

    var observer: FileAlterationObserver? = null
    var monitor: FileAlterationMonitor? = null

    override fun onStart(observer: FileAlterationObserver) {}
    override fun onDirectoryCreate(file: File) {}
    override fun onDirectoryChange(file: File) {}
    override fun onDirectoryDelete(file: File) {}
    override fun onFileCreate(file: File) {}
    override fun onFileDelete(file: File) {}
    override fun onStop(observer: FileAlterationObserver) {}
    override fun onFileChange(file: File) {
        plugin.server.schedule.task(plugin, TaskType.ASYNC, Runnable {
            val start = System.currentTimeMillis()
            FastScript.instance.scriptManager.scripts.forEach {
                when {
                    it.value.configOption.file == file -> {
                        it.value.configOption.reload()
                        plugin.server.console.sendMessage(
                            FormatHeader.INFO, languages["FILE-LISTENER.SCRIPT.LOADED"].setPlaceholder(
                            mapOf(
                                "file_name" to file.name,
                                "script_name" to it.key,
                                "millisecond" to "${System.currentTimeMillis() - start}"
                            )
                        ))
                        return@Runnable
                    }
                    it.value.scriptFiles.contains(file) -> {
                        if (it.value.init.protected) return@Runnable
                        it.value.bindExpansions().forEach { expansion ->
                            it.value.execute(expansion, plugin.server.console, "unload")
                        }
                        it.value.unregisterListeners()
                        it.value.reload()
                        plugin.server.console.sendMessage(
                            FormatHeader.INFO, languages["FILE-LISTENER.SCRIPT.LOADED"].setPlaceholder(
                            mapOf(
                                "file_name" to file.name,
                                "script_name" to it.key,
                                "millisecond" to "${System.currentTimeMillis() - start}"
                            )
                        ))
                        return@Runnable
                    }
                }
            }
        })

    }
}