package gg.aquatic.furnique.plugin

import gg.aquatic.furnique.api.FurniquePlugin
import gg.aquatic.furnique.api.furniture.FurnitureHandler

class FurniquePluginImpl: FurniquePlugin() {

    override fun onLoad() {
        INSTANCE = this
    }

    override fun onEnable() {
        FurnitureHandler.initialize()
    }

}