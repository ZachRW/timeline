package timelinejs.datastructure

data class OpenIntRange(val startExclusive: Int, val endExclusive: Int) {
    fun overlaps(other: OpenIntRange): Boolean {
        return startExclusive < other.endExclusive && other.startExclusive < endExclusive
    }
}

infix fun Int.open(to: Int) = OpenIntRange(this, to)
