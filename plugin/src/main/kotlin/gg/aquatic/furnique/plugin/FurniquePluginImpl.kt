package gg.aquatic.furnique.plugin

import gg.aquatic.furnique.api.FurniquePlugin

class FurniquePluginImpl: FurniquePlugin() {

    override fun onLoad() {
        INSTANCE = this
    }

}