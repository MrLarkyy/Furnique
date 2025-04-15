package gg.aquatic.furnique.api.model.rss.items

class RangeDispatchModelJson(
    val entries: List<Entry>,
    val fallback: ModelJson,
    val property: String,
    val scale: Double?
): ModelJson() {

    val type = "minecraft:range_dispatch"

    class Entry(
        val model: ModelJson,
        val threshold: Double
    )
}