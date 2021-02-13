package me.scoretwo.fastscript.command

import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.command.commands.ReloadCommand
import me.scoretwo.fastscript.command.commands.ScriptCommand
import me.scoretwo.fastscript.languages
import me.scoretwo.utils.command.CommandBuilder
import me.scoretwo.utils.command.CommandNexus
import me.scoretwo.utils.command.SubCommand
import me.scoretwo.utils.command.executor.Executors
import me.scoretwo.utils.command.helper.HelpGenerator
import me.scoretwo.utils.command.language.CommandLanguage
import me.scoretwo.utils.sender.GlobalSender
import net.md_5.bungee.api.chat.TextComponent

class FSCommandNexus: CommandNexus(FastScript.instance.plugin, arrayOf("FastScript", "script", "fs")) {

    init {
        register(ReloadCommand())
        register(ScriptCommand())
    }

    override var language = object : CommandLanguage {
        override val COMMAND_NO_PERMISSION = languages["COMMAND-TIPS.COMMAND_NO_PERMISSION"]
        override val COMMAND_ONLY_CONSOLE = languages["COMMAND-TIPS.COMMAND_ONLY_CONSOLE"]
        override val COMMAND_ONLY_PLAYER = languages["COMMAND-TIPS.COMMAND_ONLY_PLAYER"]
        override val COMMAND_UNKNOWN_USAGE = languages["COMMAND-TIPS.COMMAND_UNKNOWN_USAGE"]
    }
/*    override var helpGenerator = object : HelpGenerator {

        override fun translateTexts(
            command: SubCommand,
            parents: MutableList<String>,
            args: MutableList<String>
        ): MutableList<MutableList<TextComponent>> {
            return mutableListOf()
        }

    }*/

}