package gg.aquatic.furnique.api.model.model

import gg.aquatic.waves.shadow.com.retrooper.packetevents.protocol.component.ComponentTypes
import gg.aquatic.waves.shadow.com.retrooper.packetevents.protocol.component.builtin.item.ItemModel
import gg.aquatic.waves.shadow.com.retrooper.packetevents.resources.ResourceLocation
import gg.aquatic.waves.util.item.modifyFastMeta
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class AdvancedItemModel(
    val id: String,
    val model: ResourceLocation
) {

    fun apply(item: ItemStack) {
        item.modifyFastMeta {
            this.nms.setComponent(ComponentTypes.ITEM_MODEL, ItemModel(model))
        }
    }

    fun apply(material: Material): ItemStack {
        return ItemStack(material).apply { apply(this) }
    }

}