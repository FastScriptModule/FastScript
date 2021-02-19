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
            plugin.server.schedule.task(plugin, taskType) {
                try {
                    unit.invoke()
                    if (description == null)
                        plugin.server.console.sendMessage(FormatHeader.TREE, "Async ${execType.name.toLowerCase()} $execName.§8(${System.currentTimeMillis() - start}ms)")
                    else
                        plugin.server.console.sendMessage(FormatHeader.TREE, "Async ${execType.name.toLowerCase()} $execName, $description.§8(${System.currentTimeMillis() - start}ms)")
                } catch (t: Throwable) {
                    t.printStackTrace()
                    plugin.server.console.sendMessage(FormatHeader.TREE, "§cAsync failed to invoke $execName.§8(${System.currentTimeMillis() - start}ms)")
                }
            }
            return ProcessResult(ProcessResultType.SUCCESS)
        } else {
            return try {
                unit.invoke()
                if (description == null)
                    plugin.server.console.sendMessage(FormatHeader.TREE, "${execType.name} $execName.§8(${System.currentTimeMillis() - start}ms)")
                else
                    plugin.server.console.sendMessage(FormatHeader.TREE, "{execType.name} $execName, $description.§8(${System.currentTimeMillis() - start}ms)")
                ProcessResult(ProcessResultType.SUCCESS)
            } catch (t: Throwable) {
                t.printStackTrace()
                plugin.server.console.sendMessage(FormatHeader.TREE, "§cFailed to invoke $execName.§8(${System.currentTimeMillis() - start}ms)")
                ProcessResult(ProcessResultType.FAILED)
            }
        }

    }

}