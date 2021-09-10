package timelinejs.datastructure

data class Size(val width: Double, val height: Double) {
    constructor(point: AbsolutePoint) : this(point.x, point.y)

    constructor(width: Number, height: Number) : this(width.toDouble(), height.toDouble())

    companion object {
        val EMPTY = Size(0.0, 0.0)
    }

    operator fun plus(other: Size) =
        Size(width + other.width, height + other.height)

    operator fun minus(other: Size) =
        Size(width - other.width, height - other.height)

    operator fun times(scalar: Double) =
        Size(width * scalar, height * scalar)

    fun isEmpty() = width == 0.0 && height == 0.0
}