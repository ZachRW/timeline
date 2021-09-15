package timelinejs.datastructure

import timelinejs.View
import kotlin.js.Date

class RelativePoint(
    val xDate: Date,
    val y: Double,
    val view: View
) {
    constructor(xDate: Date, y: Number, view: View)
            : this(xDate, y.toDouble(), view)

    val x get() = view.dateToPx(xDate)

    operator fun plus(other: AbsolutePoint) =
        RelativePoint(
            view.pxToDate(x + other.x),
            y + other.y,
            view
        )

    operator fun minus(other: AbsolutePoint) =
        RelativePoint(
            view.pxToDate(x - other.x),
            y - other.y,
            view
        )

    fun translate(dx: Double, dy: Double) =
        this + AbsolutePoint(dx, dy)
}
