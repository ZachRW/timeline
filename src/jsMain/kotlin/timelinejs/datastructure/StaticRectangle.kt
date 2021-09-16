package timelinejs.datastructure

data class StaticRectangle(
    val x: Double,
    val y: Double,
    val width: Double,
    val height: Double
) : Rectangle {
    constructor(x: Number, y: Number, width: Number, height: Number)
            : this(x.toDouble(), y.toDouble(), width.toDouble(), height.toDouble())

    constructor(location: StaticPoint, size: Size)
            : this(location.x, location.y, size.width, size.height)

    companion object {
        val EMPTY = StaticRectangle(0.0, 0.0, 0.0, 0.0)

        fun fromEdges(left: Number, top: Number, right: Number, bottom: Number): StaticRectangle {
            val leftDouble = left.toDouble()
            val topDouble = top.toDouble()
            val rightDouble = right.toDouble()
            val bottomDouble = bottom.toDouble()
            return StaticRectangle(
                x = leftDouble,
                y = topDouble,
                width = rightDouble - leftDouble,
                height = bottomDouble - topDouble
            )
        }
    }

    val location: StaticPoint
        get() = StaticPoint(x, y)
    val size: Size
        get() = Size(width, height)

    val left get() = x
    val top get() = y
    val right get() = x + width
    val bottom get() = y + height

    val centerX get() = x + width / 2
    val centerY get() = y + height / 2

    val topLeft get() = StaticPoint(left, top)
    val topRight get() = StaticPoint(right, top)
    val bottomLeft get() = StaticPoint(left, bottom)
    val bottomRight get() = StaticPoint(right, bottom)

    val leftCenter get() = StaticPoint(left, centerY)
    val topCenter get() = StaticPoint(centerX, top)
    val rightCenter get() = StaticPoint(right, centerY)
    val bottomCenter get() = StaticPoint(centerX, bottom)
    val center get() = StaticPoint(centerX, centerY)

    fun copy(location: StaticPoint = this.location, size: Size = this.size) =
        StaticRectangle(location, size)

    fun translate(dx: Double, dy: Double) =
        copy(
            x = x + dx,
            y = y + dy
        )

    fun translate(staticPoint: StaticPoint) =
        copy(location = location + staticPoint)

    fun pointToPoint(start: StaticPoint, end: StaticPoint) =
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

    override fun toStaticRectangle() = this
}
