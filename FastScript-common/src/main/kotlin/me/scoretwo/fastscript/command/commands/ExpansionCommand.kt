package me.scoretwo.fastscript.command.commands

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.api.script.custom.CustomScript
import me.scoretwo.fastscript.command.SimpleCommand
import me.scoretwo.fastscript.sendMessage
import me.scoretwo.utils.command.executor.CommandExecutor
import me.scoretwo.utils.command.executor.Executors
import me.scoretwo.utils.sender.GlobalSender
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.chat.hover.content.Text

/**
 * @author Score2
 * @date 2021/2/19 12:26
 *
 * @project FastScript
 */
class ExpansionCommand: SimpleCommand(arrayOf("expansion")) {

    override var description = "有关拓展的设置."

    private val evaluateCommand = nextBuilder()
        .alias("evaluate", "eval")
        .description("评估指定内容获得返回值(:s 加在最后不返回消息)")
        .executor(object : Executors {
            override fun execute(sender: GlobalSender, parents: Array<String>, args: Array<String>): Boolean {
                if (args.isEmpty()) {
                    sender.sendMessage(FormatHeader.ERROR, "正确用法: /${parents.joinToString(" ")} §f<text...> §8:s")
                    return true
                }
                val noReturn = if (args.isEmpty()) false else args[args.size - 1] == ":s"

                val expansion = FastScript.instance.expansionManager.getExpansionByName(parents[parents.size - 2]) ?: FastScript.instance.expansionManager.getExpansionBySign(parents[parents.size - 2]) ?: let {
                    sender.sendMessage(FormatHeader.WARN, "找不到拓展名称或标识 §c${parents[parents.size - 2]}§7! 请检查名称.")
                    sender.sendMessage(FormatHeader.INFO, "目前可用的拓展有: §6${FastScript.instance.expansionManager.expansions.joinToString(", ") { it.name }}")
                    return true
                }

                val text = args.sliceArray(0..(if (!noReturn) args.size - 1 else args.size - 2)).joinToString("")

                val result = expansion.eval(text, sender)

                if (!noReturn) {
                    sender.sendMessage(FormatHeader.INFO, "使用拓展 §6${expansion.name} §7评估该内容的结果: ${result.toString()}")
                }

                return true
            }

            override fun tabComplete(sender: GlobalSender, parents: Array<String>, args: Array<String>) = mutableListOf(":s")
        })
        .build()


    private val infoCommand = nextBuilder()
        .alias("info")
        .description("显示有关这个脚本的信息")
        .execute(object : CommandExecutor {
            override fun execute(sender: GlobalSender, parents: Array<String>, args: Array<String>): Boolean {
                val expansion = FastScript.instance.expansionManager.getExpansionByName(parents[parents.size - 2]) ?: FastScript.instance.expansionManager.getExpansionBySign(parents[parents.size - 2]) ?: let {
                    sender.sendMessage(FormatHeader.WARN, "找不到拓展名称或标识 §c${parents[parents.size - 2]}§7! 请检查名称.")
                    sender.sendMessage(FormatHeader.INFO, "目前可用的拓展有: §6${FastScript.instance.expansionManager.expansions.joinToString(", ") { it.name }}")
                    return true
                }

                val displayParents = parents.joinToString(" ")

                val bindScripts = mutableListOf<CustomScript>().also { list -> FastScript.instance.scriptManager.scripts.forEach { if (it.value.texts.keys.contains(expansion.sign)) list.add(it.value) } }

                sender.sendMessage(FormatHeader.INFO, "拓展 §6${expansion.name} §7的相关信息:")
                sender.sendMessage("  §3§l* §7标识: §a${expansion.sign}")
                sender.sendMessage("  §3§l* §7文件后缀: §a${expansion.fileSuffix}")
                sender.sendMessage("  §3§l* §7绑定的脚本: §f${bindScripts.joinToString { it.name }}")
                sender.sendMessage("")
                sender.sendMessage("  §7查看脚本 §f${expansion.name} §7的更多帮助请输入:")
                sender.sendMessage(TextComponent("    "), TextComponent("§7/$displayParents §fhelp").also {
                    it.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, Text("§7点击自动补全命令: §f$displayParents help"))
                    it.clickEvent = ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/$displayParents help ")
                })
                sender.sendMessage("")

                return true
            }
        })
        .build()

    init {
        subCommands.clear()
        FastScript.instance.expansionManager.expansions.forEach {
            val subCommand = nextBuilder()
                .alias(it.name.toLowerCase(), it.sign.toLowerCase())
                .description("有关 ${it.name} 的命令")
                .execute(object : CommandExecutor {
                    override fun execute(sender: GlobalSender, parents: Array<String>, args: Array<String>): Boolean {
                        if (args.isEmpty()) {
                            infoCommand.execute(sender, arrayOf(*parents, "info"), arrayOf())
                            return true
                        }
                        return false
                    }
                })
                .subCommand(infoCommand)
                .subCommand(evaluateCommand)
                .build()
            subCommands.add(subCommand)
        }
    }

}