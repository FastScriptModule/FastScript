package me.scoretwo.fastscript.api.script.options.imports

import me.scoretwo.utils.configuration.ConfigurationSection
import me.scoretwo.utils.configuration.patchs.getLowerCaseNode

class ScriptImport(val name: String, val type: ScriptImportType, val obj: Object?, val met: Method?) {



    companion object {
        class Object(val clazz: String, val args: Array<Any?>)
        class Method(val name: String, val args: Array<Any?>)

        fun fromConfig(name: String, section: ConfigurationSection): ScriptImport {
            val stringType = section.getString(section.getLowerCaseNode("type"))!!.toUpperCase()
            val type = if (ScriptImportType.values().contains(ScriptImportType.valueOf(stringType)))
                ScriptImportType.valueOf(stringType)
            else
                ScriptImportType.UNKNOWN
            val objectSection = section.getConfigurationSection(section.getLowerCaseNode("object"))!!
            val obj = Object(objectSection.getString(objectSection.getLowerCaseNode("class"))!!,
                if (objectSection.contains(objectSection.getLowerCaseNode("args")))
                    objectSection.getList(objectSection.getLowerCaseNode("args"))!!.toTypedArray()
                else
                    arrayOf()
            )
            val methodSection = section.getConfigurationSection(section.getLowerCaseNode("method"))!!
            val met = Method(methodSection.getString(methodSection.getLowerCaseNode("name"))!!,
                if (methodSection.contains(methodSection.getLowerCaseNode("args")))
                    methodSection.getList(methodSection.getLowerCaseNode("args"))!!.toTypedArray()
                else
                    arrayOf()
            )

            return ScriptImport(name, type, obj, met)
        }

    }

}