package gg.aquatic.furnique.api.hitbox

import gg.aquatic.furnique.api.furniture.FurnitureHandle
import gg.aquatic.waves.fake.block.FakeBlock
import gg.aquatic.waves.interactable.Interactable
import gg.aquatic.waves.interactable.type.BlockInteractable
import gg.aquatic.waves.util.audience.GlobalAudience
import gg.aquatic.waves.util.block.AquaticBlock
import gg.aquatic.waves.util.block.impl.VanillaBlock
import org.bukkit.Material
import org.bukkit.util.Vector

class BarrierHitbox(
    val offsets: Set<Vector>
) : FurnitureHitbox {


    /*
    fun createHandle(furnitureHandle: FurnitureHandle): Collection<Interactable> {
        val rotatedOffsets =
            offsets.map { it.clone().rotateAroundY(Math.toRadians(furnitureHandle.data.location.yaw.toDouble())) }

        val interactables = ArrayList<Interactable>()
        for (vector in rotatedOffsets) {
            val loc = furnitureHandle.data.location.clone().add(vector)

            interactables += BlockInteractable(
                FakeBlock(VanillaBlock(Material.BARRIER.createBlockData()), loc, 50, GlobalAudience()) { e ->

                },
            ) { e ->
            }
        }
        return interactables
    }
     */
    override fun create(furnitureHandle: FurnitureHandle) {
        val handle = Handle(furnitureHandle, this)
        handle.spawn()
        furnitureHandle.barrierHitboxes += handle
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