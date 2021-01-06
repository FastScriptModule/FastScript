package me.scoretwo.fastscript.commands

import me.scoretwo.fastscript.hasPermission
import me.scoretwo.fastscript.sendMessage
import java.util.*
import java.util.function.Predicate
import java.util.stream.Collectors


class CommandManager {

    val commandMap = mutableMapOf<String, SubCommand>()

    lateinit var globalHelp: SubCommand

    fun initCommands() {
        globalHelp = HelpCommand()
        register(globalHelp)


    }

    fun register(subCommand: SubCommand) {
        commandMap[subCommand.name] = subCommand
    }

    fun execute(sender: Any, args: Array<String>) {
        if (args.isEmpty()) {
            if (sender.hasPermission("fastscript.${globalHelp.name}"))
                globalHelp.execute(sender, args)
            else
                sender.sendMessage("§7[§2Fast§aScript§7] §6WARN §8| §7权限不足以运行该命令.")
            return
        }

        for (command in commandMap) {
            if (command.key.toLowerCase() == args[0].toLowerCase()) {
                if (command.value.sendLimit == SubCommand.Companion.SendLimit.PERMISSION && !sender.hasPermission("fastscript.${command.key}")) {
                    sender.sendMessage("§7[§2Fast§aScript§7] §6WARN §8| §7权限不足以运行该命令.")
                    return
                }
                val rawArgs = args.clone()
                rawArgs.toMutableList().removeAt(0)

                command.value.execute(sender, rawArgs)
            }
        }
    }

    fun tabComplete(sender: Any, args: Array<String>): MutableList<String> {
        if (!sender.hasPermission("fastscript.tabcomplete")) {
            return mutableListOf()
        }
        if (args.isEmpty()) {
            return commandMap.keys.toMutableList()
        }

        if (args.size == 1) {
            return Arrays.stream(commandMap.keys.toTypedArray()).filter { it.startsWith(args[0]) }.collect(Collectors.toList())
        }

        for (command in commandMap) {
            if (command.key.toLowerCase() == args[0].toLowerCase()) {
                if (command.value.sendLimit == SubCommand.Companion.SendLimit.PERMISSION && !sender.hasPermission("fastscript.${command.key}")) {
                    return mutableListOf()
                }
                val rawArgs = args.clone()
                rawArgs.toMutableList().removeAt(0)
                return Arrays.stream(command.value.tabComplete(sender, rawArgs).toTypedArray()).filter { it.startsWith(args[args.size - 1]) }.collect(Collectors.toList())
            }
        }

        return mutableListOf()
    }

}