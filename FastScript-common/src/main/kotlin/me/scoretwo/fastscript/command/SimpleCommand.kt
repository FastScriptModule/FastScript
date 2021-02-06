package me.scoretwo.fastscript.command

import me.scoretwo.fastscript.plugin
import me.scoretwo.utils.command.SendLimit
import me.scoretwo.utils.command.SubCommand

/**
 * @author Score2
 * @date 2021/2/6 14:51
 *
 * @project FastScript
 */
abstract class SimpleCommand(alias: Array<String>, limit: SendLimit = SendLimit.ALL): SubCommand(plugin, alias, limit) {



}