package gg.aquatic.furnique.api

import org.bukkit.plugin.java.JavaPlugin

abstract class FurniquePlugin: JavaPlugin() {

    companion object {
        lateinit var INSTANCE: FurniquePlugin
    }

}