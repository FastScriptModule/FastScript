package me.scoretwo.fastscript.api.script

import me.scoretwo.utils.bukkit.configuration.yaml.ConfigurationSection
import me.scoretwo.utils.bukkit.configuration.yaml.patchs.ignoreCase
import javax.security.auth.login.Configuration

@Deprecated("现可使用 Script 中的 meta 进行获取, 且 Script 已内置这些其中的基本变量.")
interface ScriptDescription {

    val name: String
    // function name
    val main: String

    val version: String?
    val description: String?

    val authors: Array<String>

    companion object {

        fun fromSection(section: ConfigurationSection) = parseDescription(
            section.getString(section.ignoreCase("name")),
            section.getString(section.ignoreCase("main")),
            section.getString(section.ignoreCase("version")),
            section.getString(section.ignoreCase("description")),
            if (section.isList(section.ignoreCase("authors")))
                section.getStringList(section.ignoreCase("authors"))
            else
                mutableListOf(section.getString(section.ignoreCase("authors")))
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