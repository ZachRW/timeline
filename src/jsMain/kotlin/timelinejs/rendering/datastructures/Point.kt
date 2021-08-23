package timelinejs.rendering.datastructures

data class Point(val x: Double, val y: Double) {
    constructor(size: Size) : this(size.width, size.height)

    companion object {
        val EMPTY = Point(0.0, 0.0)
    }

    operator fun plus(other: Point) =
        Point(x + other.x, y + other.y)
    operator fun minus(other: Point) =
        Point(x - other.x, y - other.y)
    operator fun unaryMinus() =
        Point(-x, -y)

    fun isEmpty() = x == 0.0 && y == 0.0
}
