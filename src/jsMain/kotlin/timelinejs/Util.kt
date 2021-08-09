package timelinejs

import org.w3c.dom.CanvasRenderingContext2D as RenderContext
import org.w3c.dom.TextMetrics
import kotlin.math.PI

data class Vector2D(val x: Double, val y: Double) {
    constructor(x: Number, y: Number) : this(x.toDouble(), y.toDouble())
    constructor() : this(0.0, 0.0)

    operator fun plus(other: Vector2D) =
        Vector2D(x + other.x, y + other.y)

    operator fun minus(other: Vector2D) =
        Vector2D(x - other.x, y - other.y)

    operator fun unaryMinus() =
        Vector2D(-x, -y)

    operator fun times(other: Vector2D) =
        Vector2D(x * other.x, y * other.y)

    operator fun div(other: Vector2D) =
        Vector2D(x / other.x, y / other.y)

    operator fun times(scalar: Double) =
        Vector2D(x * scalar, y * scalar)

    operator fun div(scalar: Double) =
        Vector2D(x / scalar, y / scalar)
}

val TextMetrics.height: Double
    get() = fontBoundingBoxAscent + fontBoundingBoxDescent

inline fun RenderContext.fill(block: RenderContext.() -> Unit) {
    beginPath()
    block()
    fill()
}

inline fun RenderContext.stroke(block: RenderContext.() -> Unit) {
    beginPath()
    block()
    stroke()
}

inline fun RenderContext.circle(center: Vector2D, radius: Double) {
    arc(center.x, center.y, radius, 0.0, 2 * PI)
}

inline fun RenderContext.fillCircle(center: Vector2D, radius: Double) =
    fill { circle(center, radius) }
