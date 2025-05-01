package gg.aquatic.furnique.api.furniture

import gg.aquatic.furnique.api.hitbox.FurnitureHitbox
import gg.aquatic.furnique.api.interact.FurnitureInteraction
import gg.aquatic.furnique.api.seat.BedSeat
import gg.aquatic.furnique.api.seat.Seat
import gg.aquatic.furnique.api.visual.FurnitureVisual
import gg.aquatic.waves.item.AquaticItem
import gg.aquatic.waves.item.AquaticItemInteractEvent
import gg.aquatic.waves.registry.register
import kotlin.math.roundToInt

class Furniture(
    val id: String,
    val item: AquaticItem,
    val visual: FurnitureVisual,
    val hitbox: FurnitureHitbox,
    val seats: Set<Seat>,
    val bedSeats: Set<BedSeat>,
    val interaction: FurnitureInteraction,
) {

    init {
        item.register("furnique",id) { e->
            if (e.interactType != AquaticItemInteractEvent.InteractType.RIGHT && e.interactType != AquaticItemInteractEvent.InteractType.SHIFT_RIGHT) {
                return@register
            }

            val result = e.player.rayTraceBlocks(5.0) ?: return@register
            val block = result.hitBlock ?: return@register
            val face = result.hitBlockFace ?: return@register

            val yaw = (((e.player.yaw + 180) % 360) / 90).roundToInt() * 90f

            val location = block.location.clone().add(face.direction)
            location.yaw = yaw

            if (!hitbox.checkPlacement(location)) {
                e.player.sendMessage("You can't place this furniture here.")
                return@register
            }

            if (e.player.gameMode != org.bukkit.GameMode.CREATIVE) {
                e.itemStack.amount = e.itemStack.amount - 1
            }


            FurnitureHandler.createFurniture(location, this)
        }
    }

    class PlacementLimitation(
        val strictRotation: Boolean,
    )
}