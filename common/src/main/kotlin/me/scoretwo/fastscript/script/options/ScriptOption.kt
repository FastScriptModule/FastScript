package me.scoretwo.fastscript.script.options

import me.scoretwo.fastscript.script.options.imports.ScriptImport
import me.scoretwo.utils.configuration.ConfigurationSection
import me.scoretwo.utils.configuration.patchs.getLowerCaseNode

class ScriptOption(val engine: String, val import: MutableList<ScriptImport>, val main: String? = null) {

    companion object {

        fun fromConfig(section: ConfigurationSection): ScriptOption {
            val engine = section.getString(section.getLowerCaseNode("engine"))!!
            val main = section.getString(section.getLowerCaseNode("main"))!!

            val import = mutableListOf<ScriptImport>()

            val importSection = section.getConfigurationSection(section.getLowerCaseNode("import"))!!

            for (name in importSection.getKeys(false)) {
                import.add(ScriptImport.fromConfig(name, importSection.getConfigurationSection(name)!!))
            }

            return ScriptOption(engine, import, main)
        }


    }

}