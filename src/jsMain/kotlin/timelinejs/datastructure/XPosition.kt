package timelinejs.datastructure

sealed interface XPosition {
    fun getPx(): Double

    operator fun plus(other: AbsoluteX): XPosition
}
