package timelinejs.rendering.datastructure

data class Size(val width: Double, val height: Double) {
    constructor(point: Point) : this(point.x, point.y)

    companion object {
        val EMPTY = Size(0.0, 0.0)
    }

    operator fun plus(other: Size) =
        Size(width + other.width, height + other.height)
    operator fun minus(other: Size) =
        Size(width - other.width, height - other.height)

    fun isEmpty() = width == 0.0 && height == 0.0
}