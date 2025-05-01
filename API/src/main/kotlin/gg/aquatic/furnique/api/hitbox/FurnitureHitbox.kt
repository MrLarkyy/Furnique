package gg.aquatic.furnique.api.hitbox

import gg.aquatic.furnique.api.furniture.FurnitureHandle
import org.bukkit.Location

interface FurnitureHitbox {

    fun create(furnitureHandle: FurnitureHandle)
    fun checkPlacement(location: Location): Boolean

}