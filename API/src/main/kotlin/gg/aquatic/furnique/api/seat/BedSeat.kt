package gg.aquatic.furnique.api.seat

import gg.aquatic.furnique.api.furniture.FurnitureHandle
import org.bukkit.util.Vector

class BedSeat(
    val yaw: Float,
    val offset: Vector
) {

    fun createHandle(furnitureHandle: FurnitureHandle) = BedSeatHandle(
        furnitureHandle = furnitureHandle,
        seat = this
    )

}