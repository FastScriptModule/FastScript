package me.scoretwo.fastscript.api.utils

import me.scoretwo.utils.sender.GlobalPlayer
import me.scoretwo.utils.sender.GlobalSender
import me.scoretwo.utils.server.GlobalServer

abstract class AbstractScriptUtils {

    abstract fun toSender(sender: GlobalSender): Any?
    abstract fun toPlayer(player: GlobalPlayer): Any?
    abstract fun toServer(): Any?


}