package gg.aquatic.furnique.api.model.processed

import gg.aquatic.furnique.api.util.eulerAnglesZYX
import org.joml.Quaternionf
import org.joml.Quaternionfc
import org.joml.Vector3f


class ProcessedBone(
    val parent: ProcessedBone?,
    val pivot: Vector3f,
    val rotation: Vector3f,
) {

    val cubes = mutableListOf<ProcessedCube>()
    val children: MutableList<ProcessedBone> = mutableListOf()

    val absoluteQuaternion: Quaternionf = let {
        if (parent == null) {
            return@let Quaternionf().rotationZYX(rotation.z, rotation.y, rotation.x)
        }
        val parentQuaternion = parent.absoluteQuaternion
        val boneQuaternion = Quaternionf().rotationZYX(rotation.z, rotation.y, rotation.x)
        return@let parentQuaternion.mul(boneQuaternion)
    }

    val absoluteRotation: Vector3f = let {
        val absolute = absoluteQuaternion.eulerAnglesZYX()
        return@let absolute
    }

    val absolutePosition: Vector3f = let {
        if (parent == null) {
            return@let pivot
        }
        val parentAbsoluteQuaternion = parent.absoluteQuaternion
        val rotatedLocal = pivot.rotate(parentAbsoluteQuaternion as Quaternionfc, Vector3f())
        rotatedLocal.add(parent.absoluteRotation)
    }
}