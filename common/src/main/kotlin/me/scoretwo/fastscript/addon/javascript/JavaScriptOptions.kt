package me.scoretwo.fastscript.addon.javascript

import me.scoretwo.fastscript.api.script.ConfigScriptOptions
import me.scoretwo.utils.bukkit.configuration.yaml.file.YamlConfiguration
import me.scoretwo.utils.bukkit.configuration.yaml.patchs.getLowerCaseNode
import java.io.File

class JavaScriptOptions(file: File, section: YamlConfiguration): ConfigScriptOptions(file, section) {

    val javaScriptSection = section.getConfigurationSection(section.getLowerCaseNode("JavaScript"))

    val engine: String
    val includes = mutableMapOf<String, ScriptInclude>()

    init {
        if (!section.contains(section.getLowerCaseNode("JavaScript"))) {
            section.set("JavaScript", defaultSection)
        }

        engine = section.getString(section.getLowerCaseNode("javascript.engine"), "nashorn")

        section.getConfigurationSection(section.getLowerCaseNode("includes")).getKeys(false).forEach { key ->
            includes[key] = ScriptInclude.fromSection(section.getConfigurationSection(section.getLowerCaseNode("includes.$key")))
        }


    }

}

val defaultSection = YamlConfiguration().also {
    it.set("Includes", YamlConfiguration().also {
        it.set("utils", YamlConfiguration().also {
            it.set("Type", "OBJECT")
            it.set("Object", YamlConfiguration().also {
                it.set("Class", "me.scoretwo.fastscript.api.utils.Utils")
            })
        })
    })
    it.set("Engine", "nashorn")
}