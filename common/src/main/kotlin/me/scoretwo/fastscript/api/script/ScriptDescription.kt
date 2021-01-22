package me.scoretwo.fastscript.api.script

import me.scoretwo.utils.bukkit.configuration.yaml.ConfigurationSection
import me.scoretwo.utils.bukkit.configuration.yaml.patchs.getLowerCaseNode
import javax.security.auth.login.Configuration

interface ScriptDescription {

    val name: String
    // function name
    val main: String

    val version: String?
    val description: String?

    val authors: Array<String>

    companion object {

        fun fromSection(section: ConfigurationSection) = parseDescription(
            section.getString(section.getLowerCaseNode("name")),
            section.getString(section.getLowerCaseNode("main")),
            section.getString(section.getLowerCaseNode("version")),
            section.getString(section.getLowerCaseNode("description")),
            if (section.isList(section.getLowerCaseNode("authors")))
                section.getStringList(section.getLowerCaseNode("authors"))!!
            else
                mutableListOf(section[section.getLowerCaseNode("authors")])
        )

        fun parseDescription(
            name: String,
            main: String,
            version: String? = null,
            description: String? = null,
            authors: MutableList<String> = mutableListOf()
        ) = parseDescription(name, main, version, description, *authors.toTypedArray())

        fun parseDescription(
            name: String,
            main: String,
            version: String? = null,
            description: String? = null,
            vararg authors: String = arrayOf()
        ) = object : ScriptDescription {
            override val name: String = name
            override val main: String = main
            override val version: String? = version
            override val description: String? = description
            override val authors: Array<String> = arrayOf(*authors)

        }


    }
}