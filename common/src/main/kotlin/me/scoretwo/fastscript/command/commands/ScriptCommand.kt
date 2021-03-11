package me.scoretwo.fastscript.command.commands

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.format.FormatHeader
import me.scoretwo.fastscript.command.FSCommandNexus
import me.scoretwo.fastscript.command.SimpleCommand
import me.scoretwo.fastscript.languages
import me.scoretwo.fastscript.sendMessage
import me.scoretwo.fastscript.setPlaceholder
import me.scoretwo.utils.command.SubCommand
import me.scoretwo.utils.command.executor.CommandExecutor
import me.scoretwo.utils.command.executor.Executors
import me.scoretwo.utils.sender.GlobalPlayer
import me.scoretwo.utils.sender.GlobalSender
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.chat.hover.content.Text

/**
 * @author Score2
 * @date 2021/1/3 21:42
 *
 * @project FastScript
 */
class ScriptCommand: SimpleCommand(arrayOf("script")) {

    override var description = languages["COMMAND-NEXUS.COMMANDS.SCRIPT.DESCRIPTION"]

    init {
        instance = this
    }

    private val runCommand = nextBuilder()
        .alias("execute", "run")
        .description(languages["COMMAND-NEXUS.COMMANDS.SCRIPT.EXECUTE.DESCRIPTION"])
        .executor(object : Executors {
            override fun execute(sender: GlobalSender, parents: Array<String>, args: Array<String>): Boolean {
                val noReturn = if (args.isEmpty()) false else args[args.size - 1] == ":s"

                val script = FastScript.instance.scriptManager.scripts[parents[parents.size - 2]] ?: let {
                    // 似乎不会发生?
                    sender.sendMessage(FormatHeader.WARN, languages["COMMAND-NEXUS.COMMANDS.SCRIPT.NOT-FOUND-SCRIPT"].setPlaceholder(
                        mapOf("script_name" to parents[parents.size - 2])))
                    return true
                }
                if (args.isEmpty()) {
                    sender.sendMessage(FormatHeader.ERROR, "${languages["SUBSTANTIVE.USAGE"]}: /${parents.joinToString(" ")} §f<${script.texts.keys.joinToString("/")}> §7<${script.option.main}> <args...> §8:s")
                    return true
                }
                val sign = args[0]
                if (!script.texts.keys.contains(sign)) {
                    sender.sendMessage(FormatHeader.ERROR, "${languages["SUBSTANTIVE.USAGE"]}: /${parents.joinToString(" ")} §f<${script.texts.keys.joinToString("/")}> §7<${script.option.main}> <args...> §8:s")
                    return true
                }

                val args0: Array<Any?> = when {
                    args.size < 3 -> arrayOf()
                    noReturn -> arrayOf(*args.sliceArray(2..args.size - 2))
                    else -> arrayOf(*args.sliceArray(2 until args.size))
                }

                val result = when {
                    args.isEmpty() -> script.execute(sign, sender)
                    args.size >= 2 -> script.execute(sign, sender, args[1], args0)
                    else -> null
                }

                if (!noReturn) {
                    sender.sendMessage(FormatHeader.INFO, languages["COMMAND-NEXUS.COMMANDS.SCRIPT.EXECUTE.EXECUTE-RESULT"].setPlaceholder(
                        mapOf(
                            "script_name" to script.name,
                            "expansion_name" to (FastScript.instance.expansionManager.getExpansionBySign(sign)?.name ?: "Unknown"),
                            "result" to result.toString()
                        )))
                }

                return true
            }

            override fun tabComplete(sender: GlobalSender, parents: Array<String>, args: Array<String>): MutableList<String> {
                val scriptName = parents[parents.size - 2]
                if (args.size < 2) {
                    return mutableListOf<String>().also { list -> FastScript.instance.expansionManager.expansions.forEach { list.add(it.sign) } }
                } else if (args.size < 3) {
                    val script = FastScript.instance.scriptManager.getScript(scriptName) ?: return mutableListOf(":s")
                    return mutableListOf(script.option.main)
                }
                return mutableListOf(":s")
            }
        })
        .build()

    private val evaluateCommand = nextBuilder()
        .alias("evaluate", "eval")
        .description(languages["COMMAND-NEXUS.COMMANDS.SCRIPT.EVALUATE.DESCRIPTION"])
        .also { builder ->
            FastScript.instance.expansionManager.expansions.forEach {
                builder.customCommand(it.sign, arrayOf(), "expansion sign")
            }
        }
        .executor(object : Executors {
            override fun execute(sender: GlobalSender, parents: Array<String>, args: Array<String>): Boolean {
                val noReturn = if (args.isEmpty()) false else args[args.size - 1] == ":s"

                val script = FastScript.instance.scriptManager.scripts[parents[parents.size - 2]] ?: let {
                    // 似乎不会发生?
                    sender.sendMessage(FormatHeader.WARN, languages["COMMAND-NEXUS.COMMANDS.SCRIPT.NOT-FOUND-SCRIPT"].setPlaceholder(
                        mapOf("script_name" to parents[parents.size - 2])))
                    return true
                }
                if (args.isEmpty()) {
                    sender.sendMessage(FormatHeader.ERROR, "${languages["SUBSTANTIVE.USAGE"]}: /${parents.joinToString(" ")} §f<${script.texts.keys.joinToString("/")}> §7<args> §8:s")
                    return true
                }
                val sign = args[0]
                if (!script.texts.keys.contains(sign)) {
                    sender.sendMessage(FormatHeader.ERROR, "${languages["SUBSTANTIVE.USAGE"]}: /${parents.joinToString(" ")} §f<${script.texts.keys.joinToString("/")}>")
                    return true
                }
                val args0: Array<String> = when {
                    args.size < 2 -> arrayOf()
                    noReturn -> arrayOf(*args.sliceArray(1..args.size - 2))
                    else -> arrayOf(*args.sliceArray(1 until args.size))
                }

                val result = script.eval(sign, sender, *args0)

                if (!noReturn) {
                    sender.sendMessage(FormatHeader.INFO, languages["COMMAND-NEXUS.COMMANDS.SCRIPT.EVALUATE.EXECUTE-RESULT"].setPlaceholder(
                        mapOf(
                            "script_name" to script.name,
                            "expansion_name" to (FastScript.instance.expansionManager.getExpansionBySign(sign)?.name ?: "Unknown"),
                            "result" to result.toString()
                        )))
                }

                return true
            }

            override fun tabComplete(sender: GlobalSender, parents: Array<String>, args: Array<String>): MutableList<String> {
                if (args.size < 2) {
                    return mutableListOf<String>().also { list -> FastScript.instance.expansionManager.expansions.forEach { list.add(it.sign) } }
                }
                return mutableListOf(":s")
            }
        })
        .build()

    private val reloadCommand = nextBuilder()
        .alias("reload")
        .description(languages["COMMAND-NEXUS.COMMANDS.SCRIPT.RELOAD.DESCRIPTION"])
        .execute(object : CommandExecutor {
            override fun execute(sender: GlobalSender, parents: Array<String>, args: Array<String>): Boolean {
                val script = FastScript.instance.scriptManager.scripts[parents[parents.size - 2]] ?: let {
                    // 似乎不会发生?
                    sender.sendMessage(FormatHeader.WARN, languages["COMMAND-NEXUS.COMMANDS.SCRIPT.NOT-FOUND-SCRIPT"].setPlaceholder(
                        mapOf("script_name" to parents[parents.size - 2])))
                    return true
                }

                script.reload()
                sender.sendMessage(FormatHeader.INFO, languages["COMMAND-NEXUS.COMMANDS.SCRIPT.RELOAD.RELOADED-SCRIPT"].setPlaceholder("script_name" to script.name))
                return true
            }
        })
        .build()

    private val infoCommand = nextBuilder()
        .alias("info")
        .description(languages["COMMAND-NEXUS.COMMANDS.SCRIPT.INFO.DESCRIPTION"])
        .execute(object : CommandExecutor {
            override fun execute(sender: GlobalSender, parents: Array<String>, args: Array<String>): Boolean {
                val script = FastScript.instance.scriptManager.scripts[parents[parents.size - 2]] ?: let {
                    // 似乎不会发生?
                    sender.sendMessage(FormatHeader.WARN, languages["COMMAND-NEXUS.COMMANDS.SCRIPT.NOT-FOUND-SCRIPT"].setPlaceholder(
                        mapOf("script_name" to parents[parents.size - 2])))
                    return true
                }

                val displayParents = parents.slice(0..parents.size - 2).joinToString(" ")

                sender.sendMessage(FormatHeader.INFO, languages["COMMAND-NEXUS.COMMANDS.SCRIPT.INFO.TITLE"].setPlaceholder(
                    mapOf("script_name" to script.name)))

                languages.getList("COMMAND-NEXUS.COMMANDS.SCRIPT.INFO.TEXTS").forEach { string ->
                    sender.sendMessage(string.setPlaceholder(mapOf(
                        "script_name" to script.name,
                        "script_version" to (script.description.version ?: "1.0"),
                        "script_authors" to (if (script.description.authors.isEmpty()) "..." else script.description.authors.joinToString(", ")),
                        "script_description" to (script.description.description ?: "Not more..."),
                        "script_main" to script.description.main,
                        "script_bind_expansions" to script.bindExpansions().let { expansions ->
                            if (expansions.isEmpty())
                                return@let "Not more..."
                            mutableListOf<String>().also { signs -> expansions.forEach { expansion -> signs.add(expansion.sign) } }.joinToString()
                        }
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

    override fun execute(sender: GlobalSender, parents: Array<String>, args: Array<String>): Boolean {
        if (sender !is GlobalPlayer && args.isNotEmpty()) {
            when (args[0].toLowerCase()) {
                ":reload" -> {
                    reload()
                    return true
                }
                ":clear" -> {
                    subCommands.clear()
                    return true
                }
            }
        }
        return super.execute(sender, parents, args)
    }

    @Synchronized
    fun reload() {
        subCommands.clear()
        FastScript.instance.scriptManager.scripts.forEach {
            val subCommand = nextBuilder()
                .alias(it.value.name)
                .description(languages["COMMAND-NEXUS.COMMANDS.SCRIPT.SUB-SCRIPT-DESCRIPTION"].setPlaceholder(mapOf("script_name" to it.key)))
                .execute(object : CommandExecutor {
                    override fun execute(sender: GlobalSender, parents: Array<String>, args: Array<String>): Boolean {
                        if (args.isEmpty()) {
                            infoCommand.execute(sender, arrayOf(*parents, "info"), arrayOf())
                            return true
                        }
                        return false
                    }
                })
                .subCommand(runCommand)
                .subCommand(evaluateCommand)
                .subCommand(reloadCommand)
                .subCommand(infoCommand)
                .build()
            register(subCommand)
        }
    }

    companion object {
        lateinit var instance: ScriptCommand

    }

}