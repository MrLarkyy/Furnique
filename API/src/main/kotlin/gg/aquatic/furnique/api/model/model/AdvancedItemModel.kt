package gg.aquatic.furnique.api.model.model

import com.github.retrooper.packetevents.resources.ResourceLocation
import io.papermc.paper.datacomponent.DataComponentTypes
import net.kyori.adventure.key.Key
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class AdvancedItemModel(
    val id: String,
    val model: ResourceLocation
) {

    fun apply(item: ItemStack) {
        item.setData(DataComponentTypes.ITEM_MODEL, Key.key(id))
    }

    fun apply(material: Material): ItemStack {
        return ItemStack(material).apply { apply(this) }
    }

}