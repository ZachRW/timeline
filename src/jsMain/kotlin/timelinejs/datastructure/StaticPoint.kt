package timelinejs.datastructure

import timelinejs.View

data class StaticPoint(
    val x: Double,
    val y: Double
) : Point {
    constructor(size: Size) : this(size.width, size.height)

    constructor(x: Number, y: Number) : this(x.toDouble(), y.toDouble())

    companion object {
        val EMPTY = StaticPoint(0.0, 0.0)
    }

    operator fun plus(other: StaticPoint) =
        StaticPoint(x + other.x, y + other.y)

    operator fun minus(other: StaticPoint) =
        StaticPoint(x - other.x, y - other.y)

    operator fun unaryMinus() =
        StaticPoint(-x, -y)

    fun translate(dx: Double, dy: Double) =
        StaticPoint(this.x + dx, this.y + dy)

    fun isEmpty() = x == 0.0 && y == 0.0

    override fun toStaticPoint() = this

    fun toDynamicPoint(view: View) =
        DynamicPoint(
            xDate = view.pxToDate(x),
            y,
            view
        )
}
