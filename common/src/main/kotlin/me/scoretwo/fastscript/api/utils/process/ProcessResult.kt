package me.scoretwo.fastscript.api.utils.process

import me.scoretwo.fastscript.api.exception.ProcessException

class ProcessResult(val type: ProcessResultType, message: String? = null, vararg infos: Pair<String, String>) {

    var message: String = message ?: type.message

    val infos = mutableMapOf<String, String>()

    init {
        infos.forEach {
            this.infos[it.first] = it.second
        }
    }

    fun throwException() {
        throw ProcessException(message)
    }

    fun message(message: String) = this.also {
        this.message = message
    }

}