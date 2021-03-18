package me.scoretwo.fastscript.nukkit

import cn.nukkit.plugin.PluginBase
import me.scoretwo.fastscript.FastScript
import me.scoretwo.fastscript.api.plugin.ScriptPlugin
import me.scoretwo.fastscript.api.plugin.ScriptPluginState
import me.scoretwo.utils.nukkit.plugin.toGlobalPlugin

/**
 * @author 83669
 * @date 2021/3/18 7:30
 *
 * @project FastScript
 */
class NukkitBootStrap: PluginBase() {

    lateinit var nukkitPlugin: ScriptPlugin

    override fun onLoad() {
        nukkitPlugin = NukkitPlugin(toGlobalPlugin())

        nukkitPlugin.load()
    }

    override fun onEnable() {
        nukkitPlugin.enable()
        nukkitPlugin.reload()
        FastScript.stats = ScriptPluginState.RUNNING
    }

    override fun onDisable() {
        nukkitPlugin.disable()
        FastScript.stats = ScriptPluginState.DISABLE
    }

}