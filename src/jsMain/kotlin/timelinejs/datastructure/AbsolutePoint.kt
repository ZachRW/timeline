package timelinejs.datastructure

data class AbsolutePoint(
    override val x: Double,
    override val y: Double
) : Point {
    constructor(size: Size) : this(size.width, size.height)

    constructor(x: Number, y: Number) : this(x.toDouble(), y.toDouble())

    companion object {
        val EMPTY = AbsolutePoint(0.0, 0.0)
    }

    override operator fun plus(other: AbsolutePoint) =
        AbsolutePoint(x + other.x, y + other.y)

    operator fun minus(other: AbsolutePoint) =
        AbsolutePoint(x - other.x, y - other.y)

    operator fun unaryMinus() =
        AbsolutePoint(-x, -y)

    fun translate(x: Double, y: Double) =
        AbsolutePoint(this.x + x, this.y + y)
}
