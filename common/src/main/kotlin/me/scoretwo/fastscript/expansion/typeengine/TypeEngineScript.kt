package me.scoretwo.fastscript.expansion.typeengine

import me.scoretwo.fastscript.api.script.custom.CustomScript
import me.scoretwo.fastscript.api.script.ScriptDescription

@Deprecated("目前更改为通过expansion进行评估或执行脚本, 不需要额外的介质.")
class TypeEngineScript(name: String, val engineOption: TypeEngineScriptOptions): CustomScript(name, engineOption) {

}