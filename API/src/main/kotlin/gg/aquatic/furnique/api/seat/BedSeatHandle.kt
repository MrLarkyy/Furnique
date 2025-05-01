package gg.aquatic.furnique.api.seat

import gg.aquatic.furnique.api.furniture.FurnitureHandle
import org.bukkit.entity.Player
import org.bukkit.entity.Pose

class BedSeatHandle(
    val furnitureHandle: FurnitureHandle,
    val seat: BedSeat,
) {
    val rotatedYaw = seat.yaw + furnitureHandle.data.location.yaw
    val rotatedOffset = seat.offset.rotateAroundY(Math.toRadians(furnitureHandle.data.location.yaw.toDouble()))

    val location = furnitureHandle.data.location.clone().add(rotatedOffset).apply { this.yaw = rotatedYaw }
    val sitting = HashSet<Player>()

    fun sit(player: Player) {
        player.teleport(location)
        player.setPose(Pose.SLEEPING, true)
    }

    fun despawn() {
        for (player in sitting) {
            player.setPose(Pose.STANDING, true)
        }
        sitting.clear()
    }
}