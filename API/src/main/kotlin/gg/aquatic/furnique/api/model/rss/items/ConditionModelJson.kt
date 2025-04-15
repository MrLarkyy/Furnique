package gg.aquatic.furnique.api.model.rss.items

class ConditionModelJson(
    val on_false: ModelJson,
    val on_true: ModelJson,
    val property: String
): ModelJson() {
    val type = "minecraft:condition"

}