package me.scoretwo.fastscript.api.script.options.imports

enum class ScriptImportType {
    OBJECT_INIT, OBJECT, STATIC, UNKNOWN;

    companion object {
        fun fromString(string: String): ScriptImportType {
            for (value in ScriptImportType.values()) {
                if (value.name == string) {
                    return valueOf(string)
                }
            }
            return UNKNOWN
        }
    }
}