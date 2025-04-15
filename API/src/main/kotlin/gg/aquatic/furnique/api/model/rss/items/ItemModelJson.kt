package gg.aquatic.furnique.api.model.rss.items

class ItemModelJson(
    val parent: String,
    val textures: Textures
) {

    class Textures(
        val layer0: String,
        val particle: String
    )

}