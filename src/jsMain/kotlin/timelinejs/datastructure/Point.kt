package timelinejs.datastructure

interface Point {
    val x: Double
    val y: Double

    operator fun plus(other: AbsolutePoint): Point

    fun isEmpty() = x == 0.0 && y == 0.0
}
