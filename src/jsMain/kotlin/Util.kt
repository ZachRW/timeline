import org.w3c.dom.TextMetrics

data class Vector2D(val x: Double, val y: Double) {
    constructor(x: Number, y: Number) : this(x.toDouble(), y.toDouble())
    constructor() : this(0.0, 0.0)

    fun swapXY() = Vector2D(y, x)

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