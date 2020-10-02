package me.scoretwo.fastscript.config

import me.scoretwo.fastscript.api.yaml.YAMLObject
import java.io.File

abstract class Config: YAMLObject {

    constructor(file: File): super(file) {

    }

    abstract fun onReload()

}