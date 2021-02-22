package me.scoretwo.fastscript.command

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.command.commands.*
import me.scoretwo.fastscript.languages
import me.scoretwo.fastscript.setPlaceholder
import me.scoretwo.utils.command.CommandBuilder
import me.scoretwo.utils.command.CommandNexus
import me.scoretwo.utils.command.SubCommand
import me.scoretwo.utils.command.executor.Executors
import me.scoretwo.utils.command.helper.HelpGenerator
import me.scoretwo.utils.command.language.CommandLanguage
import me.scoretwo.utils.sender.GlobalSender
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.chat.hover.content.Text

class FSCommandNexus: CommandNexus(FastScript.instance.plugin, arrayOf("FastScript", "script", "fs")) {

    init {
        register(ScriptCommand())
        register(ExpansionCommand())
        register(ToolsCommand())
        register(ReloadCommand())
        register(DebugCommand())
    }

    override var language = object : CommandLanguage {
        override val COMMAND_NO_PERMISSION = languages["COMMAND-NEXUS.TIPS.NO-PERMISSION"]
        override val COMMAND_ONLY_CONSOLE = languages["COMMAND-NEXUS.TIPS.ONLY-CONSOLE"]
        override val COMMAND_ONLY_PLAYER = languages["COMMAND-NEXUS.TIPS.ONLY-PLAYER"]
        override val COMMAND_UNKNOWN_USAGE = languages["COMMAND-NEXUS.TIPS.UNKNOWN-USAGE"]
    }
    // copy from commons-command DefaultHelpgeneration
    override var helpGenerator = object : HelpGenerator {

        override fun translateTexts(command: SubCommand, parents: MutableList<String>, args: MutableList<String>): MutableList<MutableList<Array<TextComponent>>> {
            val texts = mutableListOf<Array<TextComponent>>()

            texts.addAll(upperModule)

            val displayParents = parents.joinToString(" ")

            command.subCommands.forEach { subCommand ->
                val displayAlia = subCommand.alias[0]
                val displayAlias =
                    if (subCommand.alias.size < 2)
                        ""
                    else
                        subCommand.alias.slice(1 until subCommand.alias.size)
                            .joinToString("/","§8[","§8]§7")

                val displayArgs =
                    if (subCommand.subCommands.isEmpty() && subCommand.customCommands.isNotEmpty())
                        "§7<${languages["SUBSTANTIVE.ARGS"]}...> "
                    else if (subCommand.subCommands.isNotEmpty()) {
                        subCommand.subCommands.joinToString("/", "§7<", "§7> ", 5, "§8...") { it.alias[0] }
                    } else
                        ""

                texts.add(arrayOf(TextComponent("§7/$displayParents §f$displayAlia$displayAlias §7$displayArgs§8§l- §7${subCommand.description}").also {
                    it.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, Text(languages["COMMAND-NEXUS.HELPER.CLICK-INSERT-COMMAND"].setPlaceholder(
                        mapOf("command" to "$displayParents $displayAlia"))))
                    it.clickEvent = ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/$displayParents $displayAlia ")
                }))
            }

            command.customCommands.forEach {
                val displayAlia = it.key
                val displayArgs = it.value.first?.joinToString("/", "§7<", "§7> ", 5, "§8...") ?: ""
                texts.add(arrayOf(TextComponent("§7/$displayParents §f$displayAlia §7$displayArgs§8§l- §7${it.value.second}").also {
                    it.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, Text(languages["COMMAND-NEXUS.HELPER.CLICK-INSERT-COMMAND"].setPlaceholder(
                        mapOf("command" to "$displayParents $displayAlia"))))
                    it.clickEvent = ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/$displayParents $displayAlia ")
                }))
            }

            if (upperModule.size == texts.size) {
                texts.add(arrayOf(TextComponent(languages["COMMAND-NEXUS.HELPER.NOT-FOUND-COMMANDS"]).also {
                    it.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, Text(languages["COMMAND-NEXUS.HELPER.NOT-FOUND-COMMANDS"]))
                }))
            }


            return mutableListOf(texts)
        }

        val upperModule = mutableListOf(
            arrayOf(TextComponent("")),
            arrayOf(
                TextComponent(" §3${plugin.description.name} §7v${plugin.description.version}").also {
                    it.hoverEvent = HoverEvent(
                        HoverEvent.Action.SHOW_TEXT, Text(languages["COMMAND-NEXUS.HELPER.CLICK-TO-GO-URL"].setPlaceholder(
                            mapOf("url" to "https://github.com/FastScriptModule/FastScript")))
                    )
                    it.clickEvent = ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/FastScriptModule/FastScript")
                },
                TextComponent(" §8§l- §bCOMMANDS")
            ),
            arrayOf(TextComponent(""))
        )

    }

}