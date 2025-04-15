package gg.aquatic.furnique.api.model.rss.items

class EquipmentModelJson(
    layer1: String,
    layer2: String,
) {

    val layers = Layers(
        humanoid = listOf(Layer(layer1)),
        humanoid_leggings = listOf(Layer(layer2))
    )

    class Layer(
        val texture: String
    )

    class Layers(
        val humanoid: Collection<Layer>,
        val humanoid_leggings: Collection<Layer>
    ) {
    }
}