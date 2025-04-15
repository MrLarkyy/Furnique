package gg.aquatic.furnique.api.model.rss.items

class SpecialModelJson(
    val model: Type,
    val base: String
): ModelJson() {

    val type = "minecraft:special"

    interface Type {
        val type: String
    }

    class Shield: Type {
        override val type = "shield"
    }




}