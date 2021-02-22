package me.scoretwo.fastscript.api.expansion

import me.scoretwo.utils.bukkit.configuration.yaml.ConfigurationSection
import java.util.*
import kotlin.NullPointerException
import kotlin.jvm.Throws

/**
 * @author Score2
 * @date 2021/2/4 21:41
 *
 * @project FastScript
 */
class ExpansionDescription(val name: String, val main: String, val version: String? = null, val authors: Array<String> = arrayOf(), val description: String? = null) {

    companion object {

        @Throws(NullPointerException::class)
        fun readConfig(section: ConfigurationSection) = ExpansionDescription(
            section.getString("name") ?: "Expansion-${UUID.randomUUID()}",
            section.getString("main") ?: throw NullPointerException("'main' cannot be null!"),
            section.getString("version"),
            section.getStringList("authors").toTypedArray(),
            section.getString("description")
        )
    }
}