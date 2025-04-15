package gg.aquatic.furnique.api.util

import org.joml.Matrix3f
import org.joml.Quaternionf
import org.joml.Vector3f
import kotlin.math.abs
import kotlin.math.asin
import kotlin.math.atan2

fun Matrix3f.toEulerZYX(result: Vector3f): Vector3f {
    result.y = asin(-Math.clamp(m02, -1.0f, 1.0f).toDouble()).toFloat()
    if (abs(m02.toDouble()) < 0.9999999) {
        result.x = atan2(m12.toDouble(), m22.toDouble()).toFloat()
        result.z = atan2(m01.toDouble(), m00.toDouble()).toFloat()
    } else {
        result.x = 0.0f
        result.z = atan2(-m10.toDouble(), m11.toDouble()).toFloat()
    }
    return result
}

fun Quaternionf.eulerAnglesZYX(eulerAngles: Vector3f = Vector3f()): Vector3f {
    return this[Matrix3f()].toEulerZYX(eulerAngles)
}