package gg.aquatic.furnique.api.hitbox

import gg.aquatic.furnique.api.furniture.FurnitureHandle
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.util.Vector

class BarrierHitbox(
    val offsets: Set<Vector>
) : FurnitureHitbox {

    override fun create(furnitureHandle: FurnitureHandle) {
        val handle = Handle(furnitureHandle, this)
        handle.spawn()
        furnitureHandle.barrierHitboxes += handle
    }

    override fun checkPlacement(location: Location): Boolean {
        val rotatedOffsets = offsets.map {
            it.clone().rotateAroundY(Math.toRadians(location.yaw.toDouble()))
        }
        for (vector in rotatedOffsets) {
            if (location.clone().add(vector).block.type != Material.AIR) return false
        }
        return true
    }

    class Handle(
        val furnitureHandle: FurnitureHandle,
        val barrierHitbox: BarrierHitbox,
    ) {

        val rotatedOffsets = barrierHitbox.offsets.map {
            it.clone().rotateAroundY(Math.toRadians(furnitureHandle.data.location.yaw.toDouble()))
        }

        fun spawn() {
            for (vector in rotatedOffsets) {
                val loc = furnitureHandle.data.location.clone().add(vector)
                loc.block.setType(Material.BARRIER, false)
            }
        }
        fun despawn() {
            for (vector in rotatedOffsets) {
                val loc = furnitureHandle.data.location.clone().add(vector)
                loc.block.setType(Material.AIR, false)
            }
        }
    }
}