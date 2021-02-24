package me.scoretwo.fastscript.api.utils

import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.plugin
import me.scoretwo.utils.server.task.TaskType
import me.scoretwo.fastscript.*
import me.scoretwo.fastscript.api.utils.process.ProcessResult
import me.scoretwo.fastscript.api.utils.process.ProcessResultType

object ExecUtils {

    fun execPeriod(execType: ExecType, execName: String, description: String? = null, taskType: TaskType = TaskType.SYNC, unit: () -> Unit): ProcessResult {
        val start = System.currentTimeMillis()

        if (taskType == TaskType.ASYNC) {
            val placeholders = mutableMapOf(
                "exec_type" to execType.name.toLowerCase(),
                "exec_name" to execName,
                "exec_description" to (description ?: ""),
                "millisecond" to "${System.currentTimeMillis() - start}"
            )
            plugin.server.schedule.task(plugin, taskType, Runnable {
                try {
                    unit.invoke()
                    if (description == null)
                        plugin.server.console.sendMessage(FormatHeader.TREE, languages["INVOKE.ASYNC-SUCCESS"].setPlaceholder(placeholders))
                    else
                        plugin.server.console.sendMessage(FormatHeader.TREE, languages["INVOKE.ASYNC-SUCCESS-HAS-DESCRIPTION"].setPlaceholder(placeholders))
                } catch (t: Throwable) {
                    plugin.server.console.sendMessage(FormatHeader.TREE, languages["INVOKE.ASYNC-FAILED"].setPlaceholder(placeholders.also { it["reason"] = t.stackTraceToString() }))
                    t.printStackTrace()
                }
            })
            return ProcessResult(ProcessResultType.SUCCESS)
        } else {
            val placeholders = mutableMapOf(
                "exec_type" to execType.name,
                "exec_name" to execName,
                "exec_description" to (description ?: ""),
                "millisecond" to "${System.currentTimeMillis() - start}"
            )
            return try {
                unit.invoke()
                if (description == null)
                    plugin.server.console.sendMessage(FormatHeader.TREE, languages["INVOKE.SUCCESS"].setPlaceholder(placeholders))
                else
                    plugin.server.console.sendMessage(FormatHeader.TREE, languages["INVOKE.SUCCESS-HAS-DESCRIPTION"].setPlaceholder(placeholders))
                ProcessResult(ProcessResultType.SUCCESS)
            } catch (t: Throwable) {
                plugin.server.console.sendMessage(FormatHeader.TREE, languages["INVOKE.FAILED"].setPlaceholder(placeholders.also { it["reason"] = t.stackTraceToString() }))
                ProcessResult(ProcessResultType.FAILED)
            }
        }

    }

}