package gg.aquatic.furnique.api.model.rss.items

class CompositeModelJson(
    val models: Collection<ModelJson>
): ModelJson() {

    val type = "minecraft:composite"

}