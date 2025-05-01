package gg.aquatic.furnique.api.furniture

import org.bukkit.Chunk
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataType
import java.util.*

class FurnitureData(
    val uuid: UUID,
    val furniture: Furniture,
    val location: Location,
    var children: HashSet<UUID>,
    var parent: UUID? = null
) {

    fun addChildren(uuid: UUID) {
        children.add(uuid)
        /*
        TODO: ADD DATA UPDATE
         */
    }

    companion object {
        val NAMESPACED_KEY = NamespacedKey("furnique", "furniture")
        val SIMPLE_DATA_KEY = NamespacedKey("furniture-data", "simple")
        val CHILDREN_KEY = NamespacedKey("furniture-data", "children")

        fun fromChunk(chunk: Chunk): List<FurnitureData> {
            val pdc = chunk.persistentDataContainer
            val container = pdc.get(NAMESPACED_KEY, PersistentDataType.TAG_CONTAINER) ?: return listOf()
            return container.keys.mapNotNull { fromChunk(chunk,UUID.fromString(it.value())) }
        }

        fun fromChunk(chunk: Chunk, uuid: UUID): FurnitureData? {
            val pdc = chunk.persistentDataContainer
            val container = pdc.get(NAMESPACED_KEY, PersistentDataType.TAG_CONTAINER) ?: return null
            val key = NamespacedKey("furniture-uuid", uuid.toString())
            val dataContainer = container.get(key, PersistentDataType.TAG_CONTAINER) ?: return null

            val dataSimple = dataContainer.get(SIMPLE_DATA_KEY, PersistentDataType.STRING)?.split(";;") ?: return null
            val dataChildren = dataContainer.get(CHILDREN_KEY, PersistentDataType.LIST.strings()) ?: listOf()

            val furnitureId = dataSimple[0]
            val locationData = dataSimple[1].split(";")

            val furniture = FurnitureHandler.furniture[furnitureId] ?: return null
            val world = chunk.world
            val location = Location(
                world,
                locationData[0].toDouble(),
                locationData[1].toDouble(),
                locationData[2].toDouble(),
                locationData[3].toFloat(),
                locationData[4].toFloat()
            )
            val parent = if (dataSimple.size > 2) UUID.fromString(dataSimple[2]) else null

            val children = HashSet<UUID>()
            for (child in dataChildren) {
                children.add(UUID.fromString(child))
            }

            return FurnitureData(uuid, furniture, location, children, parent)
        }
    }
}