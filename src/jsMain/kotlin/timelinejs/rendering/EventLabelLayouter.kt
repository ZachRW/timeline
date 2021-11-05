package timelinejs.rendering

import timelinejs.datastructure.OpenIntRange
import timelinejs.datastructure.open
import timelinejs.View
import timelinejs.rendering.compound.renderable.EventLabel

private const val EVENT_LABEL_MIN_DIST_FROM_AXIS = 100.0
private const val EVENT_LABEL_PADDING = 5.0
private const val STEM_MIN_DIST_FROM_EDGE_OF_EVENT_LABEL = 5.0

// debug flag
var reposition = true

class EventLabelLayouter(
    eventLabels: List<EventLabel>,
    private val dateAxisY: Double,
    private val view: View
) {
    private val eventLabels = eventLabels.sortedBy { it.location.xDate.getTime() }
    private val eventLabelsByRow = mutableMapOf<Int, MutableList<EventLabel>>()

    fun layout() {
        eventLabelsByRow.clear()
        putEventLabelsInAlternatingRows()
    }

    private fun positionEventLabels() {
        for (eventLabelRow in eventLabelsByRow.values) {
            moveRowEventLabelsToUnalignedPositions(eventLabelRow)
            if (!reposition) continue // debug flag

            val tooClose = eventLabelRow.getTooClose()
            do {
                for (group in tooClose) {
                    if (group.size > 1) {
                        alignAndChangeRowsOfGroupEventLabels(group)
                    }
                }
            } while (tooClose.mergeGroups())
        }
    }

    private fun putEventLabelsInAlternatingRows() {
        var top = true

        for (eventLabel in eventLabels) {
            eventLabel.moveToRow(if (top) 1 else -1)
            top = !top
        }
    }

    private fun moveRowEventLabelsToUnalignedPositions(eventLabelRow: List<EventLabel>) {
        for (eventLabel in eventLabelRow) {
            eventLabel.location = eventLabel.location.copy(
                xDate = view.datePlusPx(eventLabel.date, -eventLabel.bounds.width / 2)
            )
        }
    }

    private fun alignAndChangeRowsOfGroupEventLabels(group: List<EventLabel>) {
        alignGroup(group)
        val stemErrors = group.map(EventLabel::stemError)
        if (stemErrors.any { it > 0 }) {
            TODO()
        }
    }

    /**
     * @return whether any groups were merged
     */
    private fun MutableList<List<EventLabel>>.mergeGroups(): Boolean {
        if (size <= 1) {
            return false
        }

        var hasMerged = false
        val iterator = listIterator()
        var prevGroup = iterator.next()
        for (group in iterator) {
            if (prevGroup.last().expandedXOverlaps(group[0])) {
                val mergedGroup = prevGroup + group
                with(iterator) {
                    remove()
                    previous()
                    set(mergedGroup)
                }
                prevGroup = mergedGroup
                hasMerged = true
            } else {
                prevGroup = group
            }
        }

        return hasMerged
    }

    private fun EventLabel.moveToRow(row: Int) {
        if (row == 0) {
            error("Row 0 does not exist")
        }

        eventLabelsByRow.getOrPut(row) { mutableListOf() } += this

        val newY = if (row > 0) {
            dateAxisY - EVENT_LABEL_MIN_DIST_FROM_AXIS - bounds.height -
                    (bounds.height + EVENT_LABEL_PADDING) * (row - 1)
        } else {
            dateAxisY + EVENT_LABEL_MIN_DIST_FROM_AXIS +
                    (bounds.height + EVENT_LABEL_PADDING) * (-row - 1)
        }

        location = location.copy(y = newY)
    }

    private fun alignGroup(eventLabels: List<EventLabel>) {
        val datePxs = eventLabels.map { view.dateToPx(it.date) }
        val labelCenterPxs = mutableListOf(0.0)

        var prevHalfWidth = eventLabels[0].bounds.width / 2
        for (eventLabel in eventLabels.subList(1, eventLabels.size)) {
            val halfWidth = eventLabel.bounds.width / 2
            labelCenterPxs += labelCenterPxs.last() + prevHalfWidth + halfWidth + EVENT_LABEL_PADDING
            prevHalfWidth = halfWidth
        }

        val offset = Aligner(labelCenterPxs, datePxs).getOffset()

        var eventLabelPx = offset - eventLabels[0].bounds.width / 2
        for (eventLabel in eventLabels) {
            eventLabel.location =
                eventLabel.location.copy(xDate = view.pxToDate(eventLabelPx))
            eventLabelPx += eventLabel.bounds.width + EVENT_LABEL_PADDING
        }
    }

    private fun List<EventLabel>.getTooClose(): MutableList<List<EventLabel>> {
        val tooClose = mutableListOf<List<EventLabel>>()

        if (isEmpty()) {
            return tooClose
        }

        var groupStartIndex = 0
        while (groupStartIndex < size) {
            val groupEndIndex = getNextTooCloseGroup(groupStartIndex)
            tooClose += subList(groupStartIndex, groupEndIndex)
            groupStartIndex = groupEndIndex
        }

        return tooClose
    }

    /**
     * @param startIndex the first index of the group (inclusive).
     * @return the last index of the group (exclusive).
     */
    private fun List<EventLabel>.getNextTooCloseGroup(startIndex: Int): Int {
        if (startIndex >= lastIndex) {
            return size
        }

        var prevExpandedXRange = this[startIndex].getExpandedXRange()
        for ((index, eventLabel) in withIndex().drop(startIndex + 1)) {
            val expandedXRange = eventLabel.getExpandedXRange()
            if (!expandedXRange.overlaps(prevExpandedXRange)) {
                return index
            }
            prevExpandedXRange = expandedXRange
        }

        return size
    }

    private fun EventLabel.expandedXOverlaps(other: EventLabel): Boolean {
        return getExpandedXRange().overlaps(other.getExpandedXRange())
    }

    private fun EventLabel.getExpandedXRange(): OpenIntRange {
        return (view.dateToPx(bounds.left) - EVENT_LABEL_PADDING / 2).toInt() open
                (view.dateToPx(bounds.right) + EVENT_LABEL_PADDING / 2).toInt()
    }
}

private fun EventLabel.stemError(): Double {
    TODO()
}
