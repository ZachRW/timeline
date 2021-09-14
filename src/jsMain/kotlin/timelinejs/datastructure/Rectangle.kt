package timelinejs.datastructure

import timelinejs.View
import kotlin.js.Date

data class Rectangle(
    val location: Point,
    val size: Size
) {
    companion object {
        fun createAbsolute(x: Number, y: Number, width: Number, height: Number) =
            Rectangle(AbsolutePoint(x, y), Size(width, height))

        fun createRelative(xDate: Date, y: Number, width: Number, height: Number, view: View) =
            Rectangle(RelativePoint(xDate, y, view), Size(width, height))

        fun fromEdgesAbsolute(left: Number, top: Number, right: Number, bottom: Number): Rectangle {
            val leftDouble = left.toDouble()
            val topDouble = top.toDouble()
            val rightDouble = right.toDouble()
            val bottomDouble = bottom.toDouble()
            return createAbsolute(
                x = leftDouble,
                y = topDouble,
                width = rightDouble - leftDouble,
                height = bottomDouble - topDouble
            )
        }

        fun fromEdgesRelative(
            left: Number,
            top: Number,
            right: Number,
            bottom: Number,
            view: View
        ): Rectangle {
            val leftDouble = left.toDouble()
            val topDouble = top.toDouble()
            val rightDouble = right.toDouble()
            val bottomDouble = bottom.toDouble()

            val xDate = view.pxToDate(leftDouble)
            return createRelative(
                xDate,
                y = topDouble,
                width = rightDouble - leftDouble,
                height = bottomDouble - topDouble,
                view
            )
        }
    }

    val x get() = location.x
    val y get() = location.y
    val width get() = size.width
    val height get() = size.height

    val left get() = x
    val top get() = y
    val right get() = x + width
    val bottom get() = y + height

    val centerX get() = x + width / 2
    val centerY get() = y + height / 2

    val topLeft get() = AbsolutePoint(left, top)
    val topRight get() = AbsolutePoint(right, top)
    val bottomLeft get() = AbsolutePoint(left, bottom)
    val bottomRight get() = AbsolutePoint(right, bottom)

    val leftCenter get() = AbsolutePoint(left, centerY)
    val topCenter get() = AbsolutePoint(centerX, top)
    val rightCenter get() = AbsolutePoint(right, centerY)
    val bottomCenter get() = AbsolutePoint(centerX, bottom)
    val center get() = AbsolutePoint(centerX, centerY)

    fun translate(dx: Double, dy: Double) =
        translate(AbsolutePoint(dx, dy))

    fun translate(absolutePoint: AbsolutePoint) =
        copy(location = location + absolutePoint)

    fun pointToPoint(start: AbsolutePoint, end: AbsolutePoint) =
        copy(location = end - start)

    fun grow(deltaWidth: Double, deltaHeight: Double) =
        grow(Size(deltaWidth, deltaHeight))

    fun grow(delta: Double) =
        grow(delta, delta)

    fun grow(deltaSize: Size) =
        copy(size = size + deltaSize)

    fun centeredGrow(deltaWidth: Double, deltaHeight: Double): Rectangle {
        return when (location) {
            is AbsolutePoint -> fromEdgesAbsolute(
                left + deltaWidth,
                top + deltaHeight,
                right + deltaWidth,
                bottom + deltaHeight
            )
            is RelativePoint -> fromEdgesRelative(
                left + deltaWidth,
                top + deltaHeight,
                right + deltaWidth,
                bottom + deltaHeight,
                location.view
            )
            else -> error("This code is bad")
        }
    }

    fun centeredGrow(delta: Double) =
        centeredGrow(delta, delta)

    fun centeredGrow(deltaSize: Size) =
        centeredGrow(deltaSize.width, deltaSize.height)

    fun isEmpty() = size.isEmpty()
}
