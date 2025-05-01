package gg.aquatic.furnique.api.hitbox

import gg.aquatic.furnique.api.furniture.FurnitureHandle
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.util.Vector

class EmptyHitbox(
    val offsets: Set<Vector>
): FurnitureHitbox {
    override fun create(furnitureHandle: FurnitureHandle) {

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
}