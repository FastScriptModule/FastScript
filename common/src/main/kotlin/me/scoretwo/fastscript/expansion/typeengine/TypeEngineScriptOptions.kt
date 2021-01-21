package me.scoretwo.fastscript.expansion.typeengine

import me.scoretwo.fastscript.api.script.ScriptOptions
import me.scoretwo.utils.bukkit.configuration.yaml.file.YamlConfiguration
import me.scoretwo.utils.bukkit.configuration.yaml.patchs.getLowerCaseNode
import java.io.File

class TypeEngineScriptOptions(expansion: TypeEngineExpansion, file: File, section: YamlConfiguration): ScriptOptions(file, section) {

    val javaScriptSection = section.getConfigurationSection(section.getLowerCaseNode(expansion.sign))

    val engine: String
    val includes = mutableMapOf<String, ScriptInclude>()

    init {
        if (!section.contains(section.getLowerCaseNode(expansion.sign))) {
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

        engine = section.getString(section.getLowerCaseNode("${expansion.sign}.engine"), expansion.sign)

        section.getConfigurationSection(section.getLowerCaseNode("includes")).getKeys(false).forEach { key ->
            includes[key] =
                ScriptInclude.fromSection(section.getConfigurationSection(section.getLowerCaseNode("includes.$key")))
        }


    }
}
