package me.scoretwo.fastscript.api.format

import me.scoretwo.fastscript.languages

enum class FormatHeader {
    INFO, WARN, ERROR, TIPS, HOOKED, DEBUG, TREE;

    fun toLanguageFormat(): String {
        if (this == TREE) {
            return " §7├ "
        }

        return languages["format-header.${name}"]
    }

}