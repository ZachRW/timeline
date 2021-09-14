package timelinejs.datastructure

import timelinejs.View
import kotlin.js.Date

data class RelativeX(private val date: Date, private val view: View) : XPosition {
    override fun getPx() = view.dateToPx(date)

    override fun plus(other: AbsoluteX) =
        RelativeX(view.pxToDate(getPx() + other.x), view)
}
