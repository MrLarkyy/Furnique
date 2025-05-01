package gg.aquatic.furnique.api.visual

import gg.aquatic.furnique.api.furniture.FurnitureHandle
import gg.aquatic.waves.interactable.type.MEGInteractable
import gg.aquatic.waves.util.audience.GlobalAudience

class MEGVisual(
    val model: String,
): FurnitureVisual {
    override fun create(furnitureHandle: FurnitureHandle) {
        furnitureHandle.interactables += MEGInteractable(furnitureHandle.data.location,model, GlobalAudience()) { e->

        }
    }
}