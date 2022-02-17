package timelinejs.rendering

import collectionsjs.SortedArray
import collectionsjs.sortedArrayOf
import timelinejs.rendering.compound.renderable.EventLabel

class Row(val num: Int) : Iterable<EventLabel> {
    private val eventLabels = sortedArrayOfEventLabels()

    val size: Int get() = eventLabels.size

    private fun sortedArrayOfEventLabels(): SortedArray<EventLabel> {
        return sortedArrayOf { it.location.xDate.getTime() }
    }

    operator fun plusAssign(eventLabel: EventLabel) {
        eventLabels += eventLabel
    }

    override fun iterator(): Iterator<EventLabel> {
        return eventLabels.iterator()
    }

    fun isEmpty(): Boolean {
        return eventLabels.isEmpty()
    }

    fun mutableSlice(fromIndex: Int, toIndex: Int): MutableList<EventLabel> {
        return eventLabels.mutableSlice(fromIndex, toIndex)
    }
}