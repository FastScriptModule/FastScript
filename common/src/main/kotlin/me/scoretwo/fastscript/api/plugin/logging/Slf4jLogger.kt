package me.scoretwo.fastscript.api.plugin.logging

import org.slf4j.Logger

class Slf4jLogger(val logger: Logger): ScriptLogger {

    override fun info(msg: String) = logger.info(msg)

    override fun warn(msg: String) = logger.warn(msg)

    override fun warn(msg: String, t: Throwable) = logger.warn(msg, t)

    override fun error(msg: String) = logger.error(msg)

    override fun error(msg: String, t: Throwable) = logger.error(msg, t)

}