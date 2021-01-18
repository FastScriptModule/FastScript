package me.scoretwo.fastscript.api.addon

class AddonManager {

    val addons = mutableSetOf<Addon>()

    init {

    }

    fun register(addon: Addon) {
        addons.add(addon)
    }

    fun unregister(addon: Addon) {
        addons.remove(addon)
    }

    fun unregister(name: String) = addons.forEach {
        if (it.name == name) {
            addons.remove(it)
            return@forEach
        }
    }

}