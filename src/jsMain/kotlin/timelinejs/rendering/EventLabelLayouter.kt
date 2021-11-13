package timelinejs.rendering

import timelinejs.datastructure.OpenIntRange
import timelinejs.datastructure.open
import timelinejs.View
import timelinejs.rendering.compound.renderable.EventLabel
import kotlin.math.sign

// debug flag
var reposition = true

class EventLabelLayouter(
    eventLabels: List<EventLabel>,
    private val view: View
) {
    private val eventLabels = eventLabels.sortedBy { it.location.xDate.getTime() }
    private val eventLabelsByRow = mutableMapOf<Int, MutableList<EventLabel>>()

    fun layout() {
        resetRows()
        putEventLabelsInAlternatingRows()
        positionEventLabels()
    }

    private fun resetRows() {
        eventLabelsByRow.clear()
        for (eventLabel in eventLabels) {
            eventLabel.row = 0
        }
    }

    private fun positionEventLabels() {
        for ((_, eventLabelRow) in eventLabelsByRow) {
            eventLabelRow.forEach { it.moveToDefaultX() }
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

    private fun EventLabel.moveToDefaultX() {
        location = location.copy(
            xDate = view.datePlusPx(date, -bounds.width / 2)
        )
    }

    private fun alignAndChangeRowsOfGroupEventLabels(group: MutableList<EventLabel>) {
        alignGroup(group)

        var eventLabelsWithStemError: Map<EventLabel, Double>
        while (
            run {
                eventLabelsWithStemError = group.associateWith { it.stemError() }
                eventLabelsWithStemError.any { (_, error) -> error > 0 }
            }
        ) {
            val (maxErrorEventLabel, _) =
                eventLabelsWithStemError.maxByOrNull { (_, error) -> error }!!
            with(maxErrorEventLabel) {
                moveToRow(row + row.sign)
            }
            group -= maxErrorEventLabel
            if (group.size > 1) {
                alignGroup(group)
            } else {
                if (group.isEmpty()) error("group shouldn't be empty")
                group[0].moveToDefaultX()
                return
            }
        }
    }

    /**
     * @return whether any groups were merged
     */
    // FIXME
    private fun MutableList<MutableList<EventLabel>>.mergeGroups(): Boolean {
        if (size <= 1) {
            return false
        }

        var hasMerged = false
        val iterator = listIterator()
        var prevGroup = iterator.next()
        for (group in iterator) {
            if (prevGroup.last().expandedXOverlaps(group[0])) {
                val mergedGroup = (prevGroup + group).toMutableList()
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

    private fun EventLabel.moveToRow(newRow: Int) {
        if (newRow == 0) {
            error("Row 0 does not exist")
        }
        if (row != 0) {
            val rowEventLabels = eventLabelsByRow[row]
                ?: error("eventLabelsByRow does not match eventLabel's row")
            rowEventLabels -= this
        }

        eventLabelsByRow.getOrPut(newRow) { mutableListOf() } += this
        row = newRow
    }

    private fun alignGroup(eventLabels: List<EventLabel>) {
        val datePxs = eventLabels.map { view.dateToPx(it.date) }
        val labelCenterPxs = mutableListOf(0.0)

        var prevHalfWidth = eventLabels[0].bounds.width / 2
        for (eventLabel in eventLabels.subList(1, eventLabels.size)) {
            val halfWidth = eventLabel.bounds.width / 2
            labelCenterPxs += labelCenterPxs.last() + prevHalfWidth + halfWidth + EventLabel.PADDING
            prevHalfWidth = halfWidth
        }

        val offset = Aligner(labelCenterPxs, datePxs).getOffset()

        var eventLabelPx = offset - eventLabels[0].bounds.width / 2
        for (eventLabel in eventLabels) {
            eventLabel.location =
                eventLabel.location.copy(xDate = view.pxToDate(eventLabelPx))
            eventLabelPx += eventLabel.bounds.width + EventLabel.PADDING
        }
    }

    private fun MutableList<EventLabel>.getTooClose(): MutableList<MutableList<EventLabel>> {
        val tooClose = mutableListOf<MutableList<EventLabel>>()

        if (isEmpty()) {
            return tooClose
        }

        var groupStartIndex = 0
        while (groupStartIndex < size) {
            val groupEndIndex = getNextTooCloseGroup(groupStartIndex)
            tooClose += subList(groupStartIndex, groupEndIndex).toMutableList()
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
        return (view.dateToPx(bounds.left) - EventLabel.PADDING / 2).toInt() open
                (view.dateToPx(bounds.right) + EventLabel.PADDING / 2).toInt()
    }

    private fun EventLabel.stemError(): Double {
        val stemX = view.dateToPx(date)

        val minX = view.dateToPx(bounds.left) + EventLabel.STEM_MIN_DIST_FROM_EDGE
        val leftError = minX - stemX
        if (leftError > 0) {
            return leftError
        }

        val maxX = view.dateToPx(bounds.right) - EventLabel.STEM_MIN_DIST_FROM_EDGE
        val rightError = stemX - maxX
        if (rightError > 0) {
            return rightError
        }

        return 0.0
    }
}
