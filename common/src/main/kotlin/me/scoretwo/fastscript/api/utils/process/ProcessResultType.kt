package me.scoretwo.fastscript.api.utils.process

enum class ProcessResultType(val message: String) {
    SUCCESS("The process runs successfully."),
    FAILED("The process failed!"),
    OTHER("Other problems in the process."),
    DISABILITY("The process ran successfully, but was incomplete!")
}