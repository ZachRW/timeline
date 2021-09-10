package timelinejs.datastructure

data class AbsolutePoint(val x: Double, val y: Double) {
    constructor(size: Size) : this(size.width, size.height)

    constructor(x: Number, y: Number) : this(x.toDouble(), y.toDouble())

    companion object {
        val EMPTY = AbsolutePoint(0.0, 0.0)
    }

    operator fun plus(other: AbsolutePoint) =
        AbsolutePoint(x + other.x, y + other.y)

    operator fun minus(other: AbsolutePoint) =
        AbsolutePoint(x - other.x, y - other.y)

    operator fun unaryMinus() =
        AbsolutePoint(-x, -y)

    fun translate(x: Double, y: Double) =
        AbsolutePoint(this.x + x, this.y + y)

    fun isEmpty() = x == 0.0 && y == 0.0
}
