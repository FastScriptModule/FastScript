package me.scoretwo.fastscript.command.commands

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.command.SimpleCommand
import me.scoretwo.fastscript.debug
import me.scoretwo.fastscript.languages
import me.scoretwo.fastscript.sendMessage
import me.scoretwo.utils.command.executor.CommandExecutor
import me.scoretwo.utils.command.executor.Executors
import me.scoretwo.utils.sender.GlobalSender

/**
 * @author Score2
 * @date 2021/2/22 17:34
 *
 * @project FastScript
 */
class DebugCommand: SimpleCommand(arrayOf("debug", "test")) {

    override var description = languages["COMMAND-NEXUS.COMMANDS.DEBUG.DESCRIPTION"]

    override var subCommands = mutableListOf(
        nextBuilder().alias("languages", "language")
            .description("Debugging related to language.")
            .subCommand(
                nextBuilder().alias("config")
                    .description("Content related to configuration files.")
                    .subCommand(
                        nextBuilder().alias("sections")
                            .description("Print config all sections.")
                            .execute(object : CommandExecutor {
                                override fun execute(sender: GlobalSender, parents: Array<String>, args: Array<String>): Boolean {
                                    val name = if (args.isEmpty()) { languages.current.name } else { args[0] }
                                    if (!languages.languages.containsKey(name)) {
                                        return false
                                    }
                                    languages.languages[name]?.config?.getKeys(true)?.forEach {
                                        sender.sendMessage("§7$it: §f${languages.languages[name]?.get(it)}")
                                    }
                                    return true
                                }
                            }).also { builder ->
                                languages.languages.forEach {
                                    builder.customCommand(it.key, null, "Print ${it.key}'s sections")
                                }
                            }
                    )
                    .subCommand(
                        nextBuilder().alias("get")
                            .description("Get language content through nodes.")
                            .executor(object : Executors {
                                override fun execute(sender: GlobalSender, parents: Array<String>, args: Array<String>): Boolean {
                                    val path = args.joinToString(" ")
                                    if (!languages.current.config.contains(path)) {
                                        sender.sendMessage("Section $path not found")
                                        return true
                                    }
                                    if (languages.current.config.isList(path)) {
                                        sender.sendMessage("$path:")
                                        languages.current.config.getStringList(path).forEach {
                                            sender.sendMessage(it)
                                        }
                                        sender.sendMessage()
                                    } else {
                                        sender.sendMessage("$path: ${languages.current[path]}")
                                    }

                                    return true
                                }

                                override fun tabComplete(sender: GlobalSender, parents: Array<String>, args: Array<String>): MutableList<String>? {
                                    return languages.current.config.getKeys(true)?.toMutableList()
                                }
                            })
                    )
            )
            .build(),
        nextBuilder().alias("enable")
            .description("Enable debug mode.")
            .execute(object : CommandExecutor {
                override fun execute(sender: GlobalSender, parents: Array<String>, args: Array<String>): Boolean {
                    debug = true
                    sender.sendMessage(FormatHeader.DEBUG, "Debug mode enabled. The server will start sending messages with debug.")
                    return true
                }
            })
            .build(),
        nextBuilder().alias("disable")
            .description("Disable debug mode.")
            .execute(object : CommandExecutor {
                override fun execute(sender: GlobalSender, parents: Array<String>, args: Array<String>): Boolean {
                    debug = false
                    sender.sendMessage(FormatHeader.INFO, "Debug mode disabled.")
                    return true
                }
            })
            .build()
    )

}