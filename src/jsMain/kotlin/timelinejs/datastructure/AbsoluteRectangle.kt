package timelinejs.datastructure

data class AbsoluteRectangle(
    val x: Double,
    val y: Double,
    val width: Double,
    val height: Double
) {
    constructor(x: Number, y: Number, width: Number, height: Number)
            : this(x.toDouble(), y.toDouble(), width.toDouble(), height.toDouble())

    constructor(location: AbsolutePoint, size: Size)
            : this(location.x, location.y, size.width, size.height)

    companion object {
        val EMPTY = AbsoluteRectangle(0.0, 0.0, 0.0, 0.0)

        fun fromEdges(left: Number, top: Number, right: Number, bottom: Number): AbsoluteRectangle {
            val leftDouble = left.toDouble()
            val topDouble = top.toDouble()
            val rightDouble = right.toDouble()
            val bottomDouble = bottom.toDouble()
            return AbsoluteRectangle(
                x = leftDouble,
                y = topDouble,
                width = rightDouble - leftDouble,
                height = bottomDouble - topDouble
            )
        }
    }

    val location: AbsolutePoint get() = AbsolutePoint(x, y)
    val size: Size get() = Size(width, height)

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

    fun copy(location: AbsolutePoint = this.location, size: Size = this.size) =
        AbsoluteRectangle(location, size)

    fun translate(dx: Double, dy: Double) =
        copy(
            x = x + dx,
            y = y + dy
        )

    fun translate(absolutePoint: AbsolutePoint) =
        copy(location = location + absolutePoint)

    fun pointToPoint(start: AbsolutePoint, end: AbsolutePoint) =
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
            left + deltaWidth,
            top + deltaHeight,
            right + deltaWidth,
            bottom + deltaHeight
        )

    fun centeredGrow(delta: Double) =
        centeredGrow(delta, delta)

    fun centeredGrow(deltaSize: Size) =
        centeredGrow(deltaSize.width, deltaSize.height)

    fun isEmpty() = size.isEmpty()
}
