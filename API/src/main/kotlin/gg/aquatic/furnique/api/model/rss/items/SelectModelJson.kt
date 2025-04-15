package gg.aquatic.furnique.api.model.rss.items

class SelectModelJson(
    val cases: Collection<Case>,
    val fallback: ModelJson,
    val property: String
): ModelJson() {
    val type = "minecraft:select"

    class Case(
        val model: ModelJson,
        val `when`: String
    )
}