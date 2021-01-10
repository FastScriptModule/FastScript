package me.scoretwo.fastscript.api.plugin.logging

import java.util.logging.Level
import java.util.logging.Logger

class JavaLogger(val logger: Logger): ScriptLogger {

    override fun info(msg: String) = logger.info(msg)

    override fun warn(msg: String) = logger.warning(msg)

    override fun warn(msg: String, t: Throwable) = logger.log(Level.WARNING, msg, t)

    override fun error(msg: String) = logger.log(Level.SEVERE, msg)

    override fun error(msg: String, t: Throwable) = logger.log(Level.SEVERE, msg, t)

}