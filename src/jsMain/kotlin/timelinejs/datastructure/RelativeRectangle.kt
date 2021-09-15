package timelinejs.datastructure

import timelinejs.View
import kotlin.js.Date

data class RelativeRectangle(
    val xDate: Date,
    val y: Double,
    val width: Double,
    val height: Double,
    val view: View
) {
    constructor(xDate: Date, y: Number, width: Number, height: Number, view: View)
            : this(xDate, y.toDouble(), width.toDouble(), height.toDouble(), view)

    constructor(location: RelativePoint, size: Size, view: View)
            : this(location.xDate, location.y, size.width, size.height, view)

    companion object {
        fun fromEdges(left: Date, top: Number, right: Date, bottom: Number, view: View): RelativeRectangle {
            val topDouble = top.toDouble()
            val bottomDouble = bottom.toDouble()
            return RelativeRectangle(
                xDate = left,
                y = topDouble,
                width = view.dateToPx(right) - view.dateToPx(left),
                height = bottomDouble - topDouble,
                view
            )
        }
    }

    val location: RelativePoint get() = RelativePoint(xDate, y, view)
    val size: Size get() = Size(width, height)

    val left: Date get() = xDate
    val top: Double get() = y
    val right: Date get() = view.datePlusPx(xDate, width)
    val bottom: Double get() = y + height

    val centerX: Date get() = view.datePlusPx(xDate, width / 2)
    val centerY: Double get() = y + height / 2

    val topLeft get() = RelativePoint(left, top, view)
    val topRight get() = RelativePoint(right, top, view)
    val bottomLeft get() = RelativePoint(left, bottom, view)
    val bottomRight get() = RelativePoint(right, bottom, view)

    val leftCenter get() = RelativePoint(left, centerY, view)
    val topCenter get() = RelativePoint(centerX, top, view)
    val rightCenter get() = RelativePoint(right, centerY, view)
    val bottomCenter get() = RelativePoint(centerX, bottom, view)
    val center get() = RelativePoint(centerX, centerY, view)

    fun copy(location: RelativePoint = this.location, size: Size = this.size) =
        RelativeRectangle(location, size, view)

    fun translate(dx: Double, dy: Double) =
        copy(
            xDate = view.datePlusPx(xDate, dx),
            y = y + dy
        )

    fun translate(absolutePoint: AbsolutePoint) =
        copy(location = location + absolutePoint)

    fun pointToPoint(start: AbsolutePoint, end: RelativePoint) =
        copy(location = end - start)

    fun grow(deltaWidth: Double, deltaHeight: Double) =
        copy(
            width = width + deltaWidth,
            height = height + deltaHeight
        )

    fun grow(delta: Double) =
        grow(delta, delta)

    fun grow(deltaSize: Size) =
        copy(size = size + deltaSize)

    fun centeredGrow(deltaWidth: Double, deltaHeight: Double) =
        fromEdges(
            left = view.datePlusPx(left, deltaWidth),
            top = top + deltaHeight,
            right = view.datePlusPx(right, deltaWidth),
            bottom = bottom + deltaHeight,
            view
        )

    fun centeredGrow(delta: Double) =
        centeredGrow(delta, delta)

    fun centeredGrow(deltaSize: Size) =
        centeredGrow(deltaSize.width, deltaSize.height)

    fun isEmpty() = size.isEmpty()
}
