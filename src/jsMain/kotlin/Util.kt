data class OpenDoubleRange(val startExclusive: Double, val endExclusive: Double) {
    fun overlaps(other: OpenDoubleRange): Boolean {
        return startExclusive < other.endExclusive && other.startExclusive < endExclusive
    }
}

infix fun Double.open(to: Double) = OpenDoubleRange(this, to)
