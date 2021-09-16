package timelinejs.datastructure

import timelinejs.View
import kotlin.js.Date

data class DynamicRectangle(
    val xDate: Date,
    val y: Double,
    val width: Double,
    val height: Double,
    val view: View
) : Rectangle {
    constructor(xDate: Date, y: Number, width: Number, height: Number, view: View)
            : this(xDate, y.toDouble(), width.toDouble(), height.toDouble(), view)

    constructor(location: DynamicPoint, size: Size, view: View)
            : this(location.xDate, location.y, size.width, size.height, view)

    companion object {
        fun fromEdges(left: Date, top: Number, right: Date, bottom: Number, view: View): DynamicRectangle {
            val topDouble = top.toDouble()
            val bottomDouble = bottom.toDouble()
            return DynamicRectangle(
                xDate = left,
                y = topDouble,
                width = view.dateToPx(right) - view.dateToPx(left),
                height = bottomDouble - topDouble,
                view
            )
        }
    }

    val location: DynamicPoint
        get() = DynamicPoint(xDate, y, view)
    val size: Size
        get() = Size(width, height)

    val left: Date get() = xDate
    val top: Double get() = y
    val right: Date get() = view.datePlusPx(xDate, width)
    val bottom: Double get() = y + height

    val centerX: Date get() = view.datePlusPx(xDate, width / 2)
    val centerY: Double get() = y + height / 2

    val topLeft get() = DynamicPoint(left, top, view)
    val topRight get() = DynamicPoint(right, top, view)
    val bottomLeft get() = DynamicPoint(left, bottom, view)
    val bottomRight get() = DynamicPoint(right, bottom, view)

    val leftCenter get() = DynamicPoint(left, centerY, view)
    val topCenter get() = DynamicPoint(centerX, top, view)
    val rightCenter get() = DynamicPoint(right, centerY, view)
    val bottomCenter get() = DynamicPoint(centerX, bottom, view)
    val center get() = DynamicPoint(centerX, centerY, view)

    override fun toStaticRectangle() =
        StaticRectangle(location.toStaticPoint(), size)

    fun copy(location: DynamicPoint = this.location, size: Size = this.size) =
        DynamicRectangle(location, size, view)

    fun translate(dx: Double, dy: Double) =
        copy(
            xDate = view.datePlusPx(xDate, dx),
            y = y + dy
        )

    fun translate(staticPoint: StaticPoint) =
        copy(location = location + staticPoint)

    fun pointToPoint(start: StaticPoint, end: DynamicPoint) =
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
