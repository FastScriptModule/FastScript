package me.scoretwo.fastscript.listeners

import org.apache.commons.io.monitor.FileAlterationListener
import org.apache.commons.io.monitor.FileAlterationMonitor
import org.apache.commons.io.monitor.FileAlterationObserver
import java.io.File


class FileListener: FileAlterationListener {

    val monitor: FileAlterationMonitor

    constructor(directory: File): this(directory, 100)

    constructor(directory: File, interval: Long): this(interval, FileAlterationObserver(directory))

    constructor(interval: Long, observer: FileAlterationObserver) {
        observer.addListener(this)
        monitor = FileAlterationMonitor(interval, observer)
        monitor.start()
    }

    override fun onStart(observer: FileAlterationObserver) {

    }

    override fun onDirectoryCreate(directory: File) {}

    override fun onDirectoryChange(directory: File) {}

    override fun onDirectoryDelete(directory: File) {}

    override fun onFileCreate(file: File) {}

    override fun onFileChange(file: File) {}

    override fun onFileDelete(file: File) {}

    override fun onStop(observer: FileAlterationObserver) {}

    companion object {
        val fileListeners = mutableListOf<FileListener>()


    }
}