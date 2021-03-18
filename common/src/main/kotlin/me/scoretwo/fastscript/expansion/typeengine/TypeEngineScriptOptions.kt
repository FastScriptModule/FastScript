package me.scoretwo.fastscript.expansion.typeengine

import me.scoretwo.fastscript.api.script.custom.ConfigScriptOption
import me.scoretwo.utils.bukkit.configuration.yaml.file.YamlConfiguration
import me.scoretwo.utils.bukkit.configuration.yaml.patchs.ignoreCase
import java.io.File

@Deprecated("目前更改为通过expansion进行评估或执行脚本, 不需要额外的介质.")
class TypeEngineScriptOptions(expansion: TypeEngineExpansion, file: File, section: YamlConfiguration): ConfigScriptOption(file, section) {

    val typeEngineSection = section.getConfigurationSection(section.ignoreCase(expansion.sign))

    val engine: String
    val includes = mutableMapOf<String, ScriptInclude>()

    init {
        if (!section.contains(section.ignoreCase(expansion.sign))) {
            section.set(expansion.sign, YamlConfiguration().also {
                it.set("Includes", YamlConfiguration().also {
                    it.set("utils", YamlConfiguration().also {
                        it.set("Type", "OBJECT")
                        it.set("Object", YamlConfiguration().also {
                            it.set("Class", "me.scoretwo.fastscript.api.utils.Utils")
                        })
                    })
                })
                it.set("Engine", expansion.sign)
            })
        }

        engine = section.getString(section.ignoreCase("${expansion.sign}.engine"), expansion.sign)

        section.getConfigurationSection(section.ignoreCase("includes")).getKeys(false).forEach { key ->
            includes[key] =
                ScriptInclude.fromSection(section.getConfigurationSection(section.ignoreCase("includes.$key")))
        }


    }
}
