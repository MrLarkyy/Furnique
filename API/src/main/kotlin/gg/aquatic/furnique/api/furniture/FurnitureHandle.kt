package gg.aquatic.furnique.api.furniture

import gg.aquatic.furnique.api.hitbox.BarrierHitbox
import gg.aquatic.furnique.api.seat.BedSeatHandle
import gg.aquatic.furnique.api.seat.SeatHandle
import gg.aquatic.waves.interactable.Interactable

class FurnitureHandle internal constructor(
    val data: FurnitureData
) {

    val interactables = HashSet<Interactable>()
    val seats = HashSet<SeatHandle>()
    val bedSeats = HashSet<BedSeatHandle>()
    val barrierHitboxes = HashSet<BarrierHitbox.Handle>()

    init {
        initializeInteractables()
        initializeSeats()
        initializeHitboxes()
    }

    private fun initializeInteractables() {
        data.furniture.visual.create(this)
    }

    private fun initializeSeats() {
        for (seat in data.furniture.seats) {
            seats += seat.createHandle(this)
        }
        for (seat in data.furniture.bedSeats) {
            bedSeats += seat.createHandle(this)
        }
    }

    private fun initializeHitboxes() {
        data.furniture.hitbox.create(this)
    }

    fun destroy() {
        for (interactable in interactables) {
            interactable.destroy()
        }
        for (handle in seats) {
            handle.despawn()
        }
        for (handle in bedSeats) {
            handle.despawn()
        }
        for (handle in barrierHitboxes) {
            handle.despawn()
        }
    }

}