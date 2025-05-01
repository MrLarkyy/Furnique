package gg.aquatic.furnique.api.model.processed

import me.rochblondiaux.blockbench.element.BlockBenchElement
import org.bukkit.Axis
import org.joml.Vector3f

class ProcessedCube(
    var bone: ProcessedBone?,
    val bbElement: BlockBenchElement,
    val from: Vector3f,
    val to: Vector3f,
    val origin: Vector3f,
    val rotation: CubeRotation,
) {

    class CubeRotation(val axis: Axis, val rotation: Float)

}