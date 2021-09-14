package timelinejs.datastructure

import timelinejs.View
import kotlin.js.Date

class RelativePoint(
    private val xDate: Date,
    override val y: Double,
    val view: View
) : Point {
    constructor(xDate: Date, y: Number, view: View)
            : this(xDate, y.toDouble(), view)

    override val x get() = view.dateToPx(xDate)

    override fun plus(other: AbsolutePoint) =
        RelativePoint(
            view.pxToDate(x + other.x),
            y + other.y,
            view
        )
}
