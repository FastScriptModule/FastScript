package me.scoretwo.fastscript.config

import me.scoretwo.utils.bukkit.configuration.yaml.file.YamlConfiguration
import java.io.File

abstract class Config(val file: File) : YamlConfiguration() {

    open fun reload() {
        if (!file.exists()) {
            file.parentFile.mkdirs()
            save(file)
        } else {
            this.load(file)
            save(file)
        }
        onReload()
    }

    abstract fun onReload()

}