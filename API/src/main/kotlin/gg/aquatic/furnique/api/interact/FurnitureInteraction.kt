package gg.aquatic.furnique.api.interact

import gg.aquatic.furnique.api.furniture.FurnitureHandle
import gg.aquatic.waves.interactable.Interactable
import gg.aquatic.waves.util.generic.ConfiguredExecutableObject
import gg.aquatic.waves.util.updatePAPIPlaceholders
import org.bukkit.entity.Player

class FurnitureInteraction(
    val clicksPlayer: HashMap<Type, ConfiguredExecutableObject<Player, Unit>>,
    val clicksFurniture: HashMap<Type, ConfiguredExecutableObject<Pair<Player, FurnitureHandle>, Unit>>
) {

    fun handleClick(type: Type, player: Player, furnitureHandle: FurnitureHandle) {
        val actionsPlayer = clicksPlayer[type]
        val actionsFurniture = clicksFurniture[type]

        actionsPlayer?.execute(player) { _, str ->
            return@execute str.replace("%player%",player.name).updatePAPIPlaceholders(player)
        }
        actionsFurniture?.execute(player to furnitureHandle) { _, str ->
            return@execute str.replace("%player%",player.name).updatePAPIPlaceholders(player)
        }
    }

    enum class Type {
        LEFT,
        RIGHT,
        SHIFT_LEFT,
        SHIFT_RIGHT,
    }

}