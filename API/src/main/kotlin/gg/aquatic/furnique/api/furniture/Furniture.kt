package gg.aquatic.furnique.api.furniture

import gg.aquatic.furnique.api.hitbox.FurnitureHitbox
import gg.aquatic.furnique.api.interact.FurnitureInteraction
import gg.aquatic.furnique.api.seat.BedSeat
import gg.aquatic.furnique.api.seat.Seat
import gg.aquatic.furnique.api.visual.FurnitureVisual
import gg.aquatic.waves.item.AquaticItem
import gg.aquatic.waves.item.AquaticItemInteractEvent
import gg.aquatic.waves.registry.register

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
            val location = e.player.getTargetBlockExact(5)?.location ?: return@register
            e.itemStack.amount = e.itemStack.amount - 1

            FurnitureHandler.createFurniture(location, this)
        }
    }

    class PlacementLimitation(
        val strictRotation: Boolean,
    )
}