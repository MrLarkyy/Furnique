package gg.aquatic.furnique.api.furniture

import gg.aquatic.waves.util.event.event
import gg.aquatic.waves.util.runAsync
import org.bukkit.Location
import org.bukkit.event.world.ChunkLoadEvent
import org.bukkit.event.world.ChunkUnloadEvent
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.roundToInt

object FurnitureHandler {

    val furniture = HashMap<String, Furniture>()
    val spawnedFurniture = ConcurrentHashMap<UUID, FurnitureHandle>()
    val chunkIdToUUIDs = ConcurrentHashMap<Long, MutableCollection<UUID>>()

    fun createFurniture(location: Location, furniture: Furniture, parent: FurnitureHandle? = null): FurnitureHandle {
        val locationNew = location.clone()
        locationNew.yaw = (location.yaw / 90.0).roundToInt() * 90f

        val data = FurnitureData(
            UUID.randomUUID(),
            furniture,
            locationNew,
            HashSet(),
            parent?.data?.uuid,
        )
        if (parent != null) {
            parent.data.children.add(data.uuid)
        }
        return createFurniture(data)
    }

    fun createFurniture(data: FurnitureData): FurnitureHandle {
        val handle = FurnitureHandle(data)
        spawnedFurniture[data.uuid] = handle
        chunkIdToUUIDs.getOrPut(data.location.chunk.chunkKey) { mutableListOf() }.add(data.uuid)
        return handle
    }

    fun initialize() {
        event<ChunkLoadEvent> {
            runAsync {
                val chunkId = it.chunk.chunkKey
                val furnitureData = FurnitureData.fromChunk(it.chunk)
                for (data in furnitureData) {
                    val furniture = FurnitureHandle(data)
                    spawnedFurniture[data.uuid] = furniture
                    chunkIdToUUIDs.getOrPut(chunkId) { mutableListOf() }.add(data.uuid)
                }
            }
        }
        event<ChunkUnloadEvent> {
            val chunkId = it.chunk.chunkKey
            val uuids = chunkIdToUUIDs[chunkId] ?: return@event
            uuids.forEach { uuid ->
                spawnedFurniture[uuid]?.destroy()
                spawnedFurniture.remove(uuid)
            }
            chunkIdToUUIDs.remove(chunkId)
        }
    }
}