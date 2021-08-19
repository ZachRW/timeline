package timelinejs

data class Rectangle(
    val x: Double,
    val y: Double,
    val width: Double,
    val height: Double
) {
    constructor(pos: Vector2D, dim: Vector2D)
            : this(pos.x, pos.y, dim.x, dim.y)

    constructor(x: Number, y: Number, width: Number, height: Number)
            : this(x.toDouble(), y.toDouble(), width.toDouble(), height.toDouble())

    val left get() = x
    val right get() = x + width
    val top get() = y
    val bottom get() = y + height

    val centerX get() = x + width / 2
    val centerY get() = y + height / 2

    val topLeft get() = Vector2D(left, top)
    val topRight get() = Vector2D(right, top)
    val bottomLeft get() = Vector2D(left, bottom)
    val bottomRight get() = Vector2D(right, bottom)

    val topCenter get() = Vector2D(centerX, top)
    val bottomCenter get() = Vector2D(centerX, bottom)
    val leftCenter get() = Vector2D(left, centerY)
    val rightCenter get() = Vector2D(right, centerY)
    val center get() = Vector2D(centerX, centerY)
}