package me.scoretwo.fastscript.config

import me.scoretwo.utils.bukkit.configuration.yaml.file.YamlConfiguration
import java.io.File

abstract class Config(val file: File) : YamlConfiguration() {

    init {
        reload()
    }

    open fun reload() {
        onReload()
        this.load(file)
    }

    abstract fun onReload()

}