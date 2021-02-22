package me.scoretwo.fastscript.api.utils.process

import me.scoretwo.fastscript.api.exception.ProcessException

class ProcessResult(val type: ProcessResultType, message: String? = null) {

    var message: String = message ?: type.message

    fun throwException() {
        throw ProcessException(message)
    }

    fun message(message: String) = this.also {
        this.message = message
    }

}