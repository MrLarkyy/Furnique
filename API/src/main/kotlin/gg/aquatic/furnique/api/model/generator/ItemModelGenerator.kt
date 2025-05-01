package gg.aquatic.furnique.api.model.generator

import gg.aquatic.furnique.api.model.processed.ProcessedBone
import gg.aquatic.furnique.api.model.processed.ProcessedCube
import gg.aquatic.furnique.api.model.processed.ProcessedModel
import me.rochblondiaux.blockbench.element.face.BlockBenchElementFace
import me.rochblondiaux.blockbench.element.uv.BlockBenchUvImpl
import net.radstevee.packed.core.item.*
import net.radstevee.packed.core.key.Key
import net.radstevee.packed.core.util.Mat2x2d
import net.radstevee.packed.core.util.Vec3d
import org.joml.Vector3d
import org.joml.Vector3f
import java.util.*

object ItemModelGenerator {

    fun generate(model: ProcessedModel) {
        /*
        model.ItemModel(
            Key("furnique", UUID.randomUUID().toString()),
            null,
            ItemModelDisplay(
                null,
                null,
                null,
                null,
                null,
                ItemModelDisplayPosition(

                )
            )
        )
         */
    }

    private fun generate(cubes: Collection<ProcessedCube>, absolutePivot: Vector3f, parent: ProcessedBone?) {
        val display = ItemModelDisplay(
            null,
            null,
            null,
            null,
            ItemModelDisplayPosition(
                parent?.rotation?.toVec3d(),
                parent?.absolutePosition?.sub(absolutePivot)?.toVec3d(),
                null,
            ),
            ItemModelDisplayPosition(
                parent?.rotation?.toVec3d(),
                parent?.absolutePosition?.sub(absolutePivot)?.toVec3d(),
                null,
            ),
            ItemModelDisplayPosition(
                parent?.rotation?.toVec3d(),
                parent?.absolutePosition?.sub(absolutePivot)?.toVec3d(),
                null,
            ),
            ItemModelDisplayPosition(
                parent?.rotation?.toVec3d(),
                parent?.absolutePosition?.sub(absolutePivot)?.toVec3d(),
                null
            )
        )

        val itemModel = itemModel(
            Key("furnique", UUID.randomUUID().toString()),
        ) {
            cubes {
                for (cube in cubes) {
                    cube {
                        rotation {
                            axis = cube.rotation.axis.toString().lowercase()
                            angle = cube.rotation.rotation
                            origin = cube.origin.toVec3d()
                        }
                        from = cube.from.toVec3d()
                        to = cube.to.toVec3d()
                        faces = cube.bbElement.faces().toCubeFaces()
                    }
                }
            }
            this.display = display
        }
    }

    private fun BlockBenchElementFace.toCubeFaces(): CubeFaces {
        return CubeFaces.Builder().apply {
            up {
                uv = this@toCubeFaces.up.toMat2x2d()
                texture = this@toCubeFaces.up.rawTexture
                tintIndex = 0
            }
            down {
                uv = this@toCubeFaces.down.toMat2x2d()
                texture = this@toCubeFaces.down.rawTexture
                tintIndex = 0
            }
            east {
                uv = this@toCubeFaces.east.toMat2x2d()
                texture = this@toCubeFaces.east.rawTexture
                tintIndex = 0
            }
            north {
                uv = this@toCubeFaces.north.toMat2x2d()
                texture = this@toCubeFaces.north.rawTexture
                tintIndex = 0
            }
            west {
                uv = this@toCubeFaces.west.toMat2x2d()
                texture = this@toCubeFaces.west.rawTexture
                tintIndex = 0
            }
            south {
                uv = this@toCubeFaces.south.toMat2x2d()
                texture = this@toCubeFaces.south.rawTexture
                tintIndex = 0
            }
        }.build()
    }

    private fun BlockBenchUvImpl.toMat2x2d(): Mat2x2d {
        return Mat2x2d(
            uv.getOrNull(0)?.toDouble() ?: .0,
            uv.getOrNull(1)?.toDouble() ?: .0,
            uv.getOrNull(2)?.toDouble() ?: .0,
            uv.getOrNull(3)?.toDouble() ?: .0
        )
    }

    private fun MutableList<Cube.Builder>.cube(builder: Cube.Builder.() -> Unit) {
        add(Cube.Builder().apply(builder))
    }

    private fun generateTextures(cubes: Collection<ProcessedCube>): Map<String, Key> {
        val map = mutableMapOf<String, Key>()
        return map
    }

    private fun Vector3d.toVec3d(): Vec3d {
        return Vec3d(x, y, z)
    }

    private fun Vector3f.toVec3d(): Vec3d {
        return Vec3d(x.toDouble(), y.toDouble(), z.toDouble())
    }

}