package timelinejs.datastructure

data class Point(val x: Double, val y: Double) {
    constructor(size: Size) : this(size.width, size.height)

    constructor(x: Number, y: Number) : this(x.toDouble(), y.toDouble())

    companion object {
        val EMPTY = Point(0.0, 0.0)
    }

    operator fun plus(other: Point) =
        Point(x + other.x, y + other.y)

    operator fun minus(other: Point) =
        Point(x - other.x, y - other.y)

    operator fun unaryMinus() =
        Point(-x, -y)

    fun translate(x: Double, y: Double) =
        Point(this.x + x, this.y + y)

    fun isEmpty() = x == 0.0 && y == 0.0
}
