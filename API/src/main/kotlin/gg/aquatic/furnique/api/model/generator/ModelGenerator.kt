package gg.aquatic.furnique.api.model.generator

import gg.aquatic.furnique.api.model.processed.ProcessedBone
import gg.aquatic.furnique.api.model.processed.ProcessedCube
import gg.aquatic.furnique.api.model.processed.ProcessedModel
import gg.aquatic.waves.shadow.com.retrooper.packetevents.protocol.world.states.enums.Axis
import me.rochblondiaux.blockbench.element.BlockBenchElement
import me.rochblondiaux.blockbench.model.BlockBenchModel
import me.rochblondiaux.blockbench.outliner.Outliner
import org.joml.Vector3f
import java.util.*

class ModelGenerator(
    val model: BlockBenchModel,
) {

    fun processModel(model: BlockBenchModel): ProcessedModel {
        val processedCubes = mutableSetOf<UUID>()
        val groups = model.outliner().map { processOutliner(it, null, processedCubes) }
        val localCubes =
            model.elements().filter { !processedCubes.contains(it.uniqueId()) }.map { processCube(it, null) }
                .toMutableList()
        for (processedBone in groups.filter { it.rotation == Vector3f() }) {
            localCubes += processedBone.cubes.map {
                transferCube(processedBone.pivot, processedBone.pivot, it).apply {
                    it.bone = null
                }
            }
            processedBone.cubes.clear()
        }

        return ProcessedModel(model.name(), groups, localCubes)
    }

    private fun processOutliner(
        outliner: Outliner,
        parent: ProcessedBone?,
        processedCubes: MutableSet<UUID>
    ): ProcessedBone {
        /*
        TODO: ADD ROTATION PROCESSING
         */
        val bone = ProcessedBone(parent, outliner.origin().toVector3f(), Vector3f())
        for (child in outliner.children()) {
            val uuid = child.uniqueId()
            val element = model.elements().find { it.uniqueId() == uuid }

            if (element == null) {
                val childBone = processOutliner(child, bone, processedCubes)
                if (childBone.rotation == bone.rotation) {
                    bone.cubes += childBone.cubes.map {
                        transferCube(childBone.pivot, bone.pivot, it).apply { it.bone = bone }
                    }
                    childBone.cubes.clear()
                }
                bone.children += childBone
                continue
            }
            if (element.type() == "cube") {
                val processedCube = processCube(element, bone)
                bone.cubes += processedCube
                processedCubes.add(uuid)
            }
        }
        return bone
    }

    private fun processCube(element: BlockBenchElement, parent: ProcessedBone?): ProcessedCube {
        val originalRotation = element.rotation().toVector3f()
        val rotation = if (originalRotation.x == 0f && originalRotation.y != 0f && originalRotation.z == 0f) {
            ProcessedCube.CubeRotation(Axis.Y, originalRotation.y)
        } else if (originalRotation.x == 0f && originalRotation.y == 0f && originalRotation.z != 0f) {
            ProcessedCube.CubeRotation(Axis.Z, originalRotation.z)
        } else ProcessedCube.CubeRotation(Axis.X, originalRotation.x)

        return ProcessedCube(
            parent,
            element,
            element.from().toVector3f(),
            element.to().toVector3f(),
            element.origin().toVector3f(),
            rotation
        )
    }

    private fun transferCube(fromPivot: Vector3f, toPivot: Vector3f, cube: ProcessedCube): ProcessedCube {
        if (fromPivot == toPivot) return cube

        val fromDelta = fromPivot.sub(cube.origin, Vector3f())
        val toDelta = toPivot.sub(cube.origin, Vector3f())

        return ProcessedCube(
            cube.bone,
            cube.bbElement,
            cube.from.add(fromDelta),
            cube.to.add(toDelta),
            cube.origin,
            cube.rotation
        )
    }

    private fun Array<Float>.toVector3f(): Vector3f {
        return Vector3f(
            getOrNull(0) ?: 0f,
            getOrNull(1) ?: 0f,
            getOrNull(2) ?: 0f
        )
    }

    private fun FloatArray.toVector3f(): Vector3f {
        return Vector3f(
            getOrNull(0) ?: 0f,
            getOrNull(1) ?: 0f,
            getOrNull(2) ?: 0f
        )
    }

}