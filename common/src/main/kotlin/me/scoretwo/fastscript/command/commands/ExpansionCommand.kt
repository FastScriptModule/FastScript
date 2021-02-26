package me.scoretwo.fastscript.command.commands

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.api.script.custom.CustomScript
import me.scoretwo.fastscript.command.FSCommandNexus
import me.scoretwo.fastscript.command.SimpleCommand
import me.scoretwo.fastscript.languages
import me.scoretwo.fastscript.sendMessage
import me.scoretwo.fastscript.setPlaceholder
import me.scoretwo.utils.command.executor.CommandExecutor
import me.scoretwo.utils.command.executor.Executors
import me.scoretwo.utils.sender.GlobalSender
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.chat.hover.content.Text
import org.jetbrains.kotlin.util.capitalizeDecapitalize.capitalizeFirstWord

/**
 * @author Score2
 * @date 2021/2/19 12:26
 *
 * @project FastScript
 */
class ExpansionCommand: SimpleCommand(arrayOf("expansion")) {

    override var description = languages["COMMAND-NEXUS.COMMANDS.EXPANSION.DESCRIPTION"]

    private val evaluateCommand = nextBuilder()
        .alias("evaluate", "eval")
        .description(languages["COMMAND-NEXUS.COMMANDS.EXPANSION.EVALUATE.DESCRIPTION"])
        .executor(object : Executors {
            override fun execute(sender: GlobalSender, parents: Array<String>, args: Array<String>): Boolean {
                if (args.isEmpty()) {
                    sender.sendMessage(FormatHeader.ERROR, "${languages["SUBSTANTIVE.USAGE"].capitalizeFirstWord()}: /${parents.joinToString(" ")} §f<text...> §8:s")
                    return true
                }
                val noReturn = if (args.isEmpty()) false else args[args.size - 1] == ":s"

                val expansion = FastScript.instance.expansionManager.getExpansionByName(parents[parents.size - 2]) ?: FastScript.instance.expansionManager.getExpansionBySign(parents[parents.size - 2]) ?: let {
                    sender.sendMessage(FormatHeader.WARN, languages["COMMAND-NEXUS.COMMANDS.EXPANSION.NOT-FOUND-NAME-OR-SIGN"].setPlaceholder(
                        mapOf("expansion_name" to parents[parents.size - 2])
                    ))
                    sender.sendMessage(FormatHeader.INFO, languages["COMMAND-NEXUS.COMMANDS.EXPANSION.LOADED-EXPANSIONS"].setPlaceholder(
                        mapOf("expansions" to FastScript.instance.expansionManager.expansions.joinToString(", ") { it.name })
                    ))
                    return true
                }

                val text = args.sliceArray(0..(if (!noReturn) args.size - 1 else args.size - 2)).joinToString("")

                val result = expansion.eval(text, sender)

                if (!noReturn) {
                    sender.sendMessage(FormatHeader.INFO, languages["COMMAND-NEXUS.COMMANDS.EXPANSION.EVALUATE.EVALUATE-RESULT"].setPlaceholder(
                        mapOf(
                            "expansion_name" to expansion.name,
                            "result" to result.toString()
                        )
                    ))
                }

                return true
            }

            override fun tabComplete(sender: GlobalSender, parents: Array<String>, args: Array<String>) = mutableListOf(":s")
        })
        .build()


    private val infoCommand = nextBuilder()
        .alias("info")
        .description(languages["COMMAND-NEXUS.COMMANDS.EXPANSION.INFO.DESCRIPTION"])
        .execute(object : CommandExecutor {
            override fun execute(sender: GlobalSender, parents: Array<String>, args: Array<String>): Boolean {
                val expansion = FastScript.instance.expansionManager.getExpansionByName(parents[parents.size - 2]) ?: FastScript.instance.expansionManager.getExpansionBySign(parents[parents.size - 2]) ?: let {
                    sender.sendMessage(FormatHeader.WARN, languages["COMMAND-NEXUS.COMMANDS.EXPANSION.NOT-FOUND-NAME-OR-SIGN"].setPlaceholder(
                        mapOf("expansion_name" to parents[parents.size - 2])
                    ))
                    sender.sendMessage(FormatHeader.INFO, languages["COMMAND-NEXUS.COMMANDS.EXPANSION.LOADED-EXPANSIONS"].setPlaceholder(
                        mapOf("expansions" to FastScript.instance.expansionManager.expansions.joinToString(", ") { it.name })
                    ))
                    return true
                }

                val displayParents = parents.slice(0..parents.size - 2).joinToString(" ")

                val bindScripts = mutableListOf<CustomScript>().also { list -> FastScript.instance.scriptManager.scripts.forEach { if (it.value.texts.keys.contains(expansion.sign)) list.add(it.value) } }

                sender.sendMessage(FormatHeader.INFO, languages["COMMAND-NEXUS.COMMANDS.EXPANSION.INFO.TITLE"].setPlaceholder(
                    mapOf("expansion_name" to expansion.name)))
                languages.getList("COMMAND-NEXUS.COMMANDS.EXPANSION.INFO.TEXTS").forEach {
                    sender.sendMessage(it.setPlaceholder(mapOf(
                        "expansion_name" to expansion.name,
                        "expansion_sign" to expansion.sign,
                        "expansion_file_suffix" to expansion.fileSuffix,
                        "expansion_bind_scripts" to bindScripts.joinToString { it.name }
                    )))
                }
                sender.sendMessage(TextComponent("    "), TextComponent("§7/$displayParents §fhelp").also {
                    if (FSCommandNexus.safeMode) return@also
                    it.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, Text(languages["COMMAND-NEXUS.HELPER.CLICK-INSERT-COMMAND"].setPlaceholder(
                        mapOf("command" to "$displayParents help")
                    )))
                    it.clickEvent = ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/$displayParents help ")
                })
                sender.sendMessage()

                return true
            }
        })
        .build()

    init {
        subCommands.clear()
        FastScript.instance.expansionManager.expansions.forEach {
            val subCommand = nextBuilder()
                .alias(it.name.toLowerCase(), it.sign.toLowerCase())
                .description(languages["COMMAND-NEXUS.COMMANDS.EXPANSION.SUB-EXPANSION-DESCRIPTION"].setPlaceholder(mapOf("expansion_name" to it.name)))
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