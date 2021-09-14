package timelinejs.datastructure

data class AbsoluteX(val x: Double) : XPosition {
    override fun getPx() = x

    override fun plus(other: AbsoluteX) =
        AbsoluteX(x + other.x)
}
