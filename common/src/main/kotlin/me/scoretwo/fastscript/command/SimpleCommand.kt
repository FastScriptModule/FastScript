package me.scoretwo.fastscript.command

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.plugin
import me.scoretwo.utils.command.CommandBuilder
import me.scoretwo.utils.command.SendLimit
import me.scoretwo.utils.command.SubCommand
import me.scoretwo.utils.command.helper.HelpGenerator

/**
 * @author Score2
 * @date 2021/2/6 14:51
 *
 * @project FastScript
 */
abstract class SimpleCommand(alias: Array<String>, limit: SendLimit = SendLimit.ALL): SubCommand(plugin, alias, limit) {

    override var helpGenerator = FSCommandNexus.helpGenerator

}