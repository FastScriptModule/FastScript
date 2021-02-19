package me.scoretwo.fastscript.command.commands

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.command.SimpleCommand
import me.scoretwo.utils.command.SubCommand

/**
 * @author Score2
 * @date 2021/2/19 12:26
 *
 * @project FastScript
 */
class ExpansionCommand: SimpleCommand(arrayOf("expansion")) {

    override var description = "有关拓展的设置."

    init {
        subCommands.clear()
        FastScript.instance.expansionManager.expansions.forEach {
            val subCommand = nextBuilder().alias(it.name.toLowerCase(), it.sign.toLowerCase())
                .description("有关 ${it.name} 的命令")
                .build()
            subCommands.add(subCommand)
        }
    }

}