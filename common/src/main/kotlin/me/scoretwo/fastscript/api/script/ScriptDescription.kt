package me.scoretwo.fastscript.api.script

interface ScriptDescription {

    val name: String
    // function name
    val main: String

    val version: String?
    val description: String?

    val authors: Array<String>

    companion object {
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