package me.scoretwo.fastscript.nukkit

import cn.nukkit.Server
import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.plugin.ScriptPlugin
import me.scoretwo.utils.nukkit.command.toNukkitSender
import me.scoretwo.utils.plugin.GlobalPlugin
import me.scoretwo.utils.sender.GlobalPlayer
import me.scoretwo.utils.sender.GlobalSender

/**
 * @author 83669
 * @date 2021/3/18 7:41
 *
 * @project FastScript
 */
class NukkitPlugin(plugin: GlobalPlugin): ScriptPlugin(plugin) {

    override fun load() {
        FastScript.setBootstrap(this)
    }

    override fun enable() {
        FastScript.instance.reload("script", "plugin")
    }

    override fun setPlaceholder(player: GlobalPlayer, string: String): String {
        return string
    }

    override fun toOriginalSender(sender: GlobalSender): Any = sender.toNukkitSender()

    override fun toOriginalPlayer(player: GlobalPlayer): Any = player.toNukkitSender()

    override fun toOriginalServer(): Any = Server.getInstance()

    override fun reload() {

    }


}