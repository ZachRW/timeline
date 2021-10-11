package timelinejs.datastructure

import timelinejs.View
import kotlin.js.Date

data class DynamicPoint(
    val xDate: Date,
    val y: Double,
    val view: View
) : Point {
    constructor(xDate: Date, y: Number, view: View)
            : this(xDate, y.toDouble(), view)

    val x get() = view.dateToPx(xDate)

    operator fun plus(other: StaticPoint) =
        DynamicPoint(
            view.pxToDate(x + other.x),
            y + other.y,
            view
        )

    operator fun minus(other: StaticPoint) =
        DynamicPoint(
            view.pxToDate(x - other.x),
            y - other.y,
            view
        )

    fun translate(dx: Double, dy: Double) =
        this + StaticPoint(dx, dy)

    override fun toStaticPoint() = StaticPoint(x, y)
}
