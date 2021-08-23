package timelinejs.rendering.datastructures

data class Rectangle(
    val x: Double,
    val y: Double,
    val width: Double,
    val height: Double
) {
    constructor(location: Point, size: Size)
            : this(location.x, location.y, size.width, size.height)

    companion object {
        val EMPTY = Rectangle(0.0, 0.0, 0.0, 0.0)

        fun fromEdges(left: Double, top: Double, right: Double, bottom: Double) =
            Rectangle(left, top, right - left, bottom - top)
    }

    val location: Point get() = Point(x, y)
    val size: Size get() = Size(width, height)

    val left get() = x
    val top get() = y
    val right get() = x + width
    val bottom get() = y + height

    val centerX get() = x + width / 2
    val centerY get() = y + height / 2

    val topLeft get() = Point(left, top)
    val topRight get() = Point(right, top)
    val bottomLeft get() = Point(left, bottom)
    val bottomRight get() = Point(right, bottom)

    val leftCenter get() = Point(left, centerY)
    val topCenter get() = Point(centerX, top)
    val rightCenter get() = Point(right, centerY)
    val bottomCenter get() = Point(centerX, bottom)
    val center get() = Point(centerX, centerY)

    fun copy(location: Point = this.location, size: Size = this.size) =
        Rectangle(location, size)

    fun translate(dx: Double, dy: Double) =
        copy(
            x = x + dx,
            y = y + dy
        )

    fun translate(point: Point) =
        copy(location = location + point)

    fun pointToPoint(start: Point, end: Point) =
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
