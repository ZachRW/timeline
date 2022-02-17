package timelinejs.rendering

import timelinejs.datastructure.OpenIntRange
import timelinejs.datastructure.open
import timelinejs.View
import timelinejs.rendering.compound.renderable.EventLabel
import collectionsjs.*
import timelinejs.rendering.compound.renderable.debugEventLabels

// debug variable
var reposition = true

class EventLabelLayouter(
    eventLabels: List<EventLabel>,
    private val view: View
) {
    private val rowHandler: RowHandler

    init {
        val sortedEventLabels = eventLabels.sortedBy { it.location.xDate.getTime() }
        rowHandler = RowHandler(sortedEventLabels)
    }

    fun layout() {
        rowHandler.reset()
        positionEventLabels()
    }

    private fun positionEventLabels() {
        // FIXME using rowsWithNum instead of rows breaks stuff for some reason
        for (row in rowHandler.rows) {
//            console.log("Positioning row $rowNum")
//
//            console.log("Centering event labels in row $rowNum on their event date")
            row.forEach { it.moveToDefaultX() }
            if (!reposition) continue // stop prematurely (only for debugging)

            val tooClose = eventLabelRow.getTooClose()
            do {
                for (group in tooClose) {
                    alignAndChangeRowsOfGroupEventLabels(group)
                }
            } while (tooClose.mergeOverlappingGroups())
        }
    }

    private fun EventLabel.moveToDefaultX() {
        if (textStr in debugEventLabels) {
            console.log("Centering '$textStr' on its event date")
        }
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
//            val (eventLabelToMove, _) =
//                eventLabelsWithStemError.maxByOrNull { (_, error) -> error }!!
            val eventLabelToMove = group.last()
            rowHandler.moveEventLabelOutRow(eventLabelToMove)
            group -= eventLabelToMove
            alignGroup(group)
        }
    }

    /**
     * @return whether any groups were merged
     */
    private fun MutableList<MutableList<EventLabel>>.mergeOverlappingGroups(): Boolean {
        if (size <= 1) {
            return false
        }

        var hasMerged = false
        val iterator = listIterator()
        var prevGroup = iterator.next()
        for (group in iterator) {
            if (prevGroup.last().expandedXOverlaps(group[0])) {
                prevGroup = iterator.mergeGroups(group)
                hasMerged = true
            } else {
                prevGroup = group
            }
        }

        return hasMerged
    }

    private fun MutableListIterator<MutableList<EventLabel>>.mergeGroups(currentGroup: List<EventLabel>):
            MutableList<EventLabel> {
        remove()
        val prevGroup = previous()
        val mergedGroup = (prevGroup + currentGroup).toMutableList()

        set(mergedGroup)
        if (hasNext()) {
            next()
        }

        return mergedGroup
    }

    private fun alignGroup(group: List<EventLabel>) {
        if (group.isEmpty()) error("group shouldn't be empty")
        if (group.size == 1) {
            group[0].moveToDefaultX()
            return
        }

        val datePxs = group.map { view.dateToPx(it.date) }
        val labelCenterPxs = mutableListOf(0.0)

        var prevHalfWidth = group[0].bounds.width / 2
        for (eventLabel in group.subList(1, group.size)) {
            val halfWidth = eventLabel.bounds.width / 2
            labelCenterPxs += labelCenterPxs.last() + prevHalfWidth + halfWidth + EventLabel.PADDING
            prevHalfWidth = halfWidth
        }

        val offset = Aligner(labelCenterPxs, datePxs).getOffset()

        var eventLabelPx = offset - group[0].bounds.width / 2
        for (eventLabel in group) {
            eventLabel.location =
                eventLabel.location.copy(xDate = view.pxToDate(eventLabelPx))
            eventLabelPx += eventLabel.bounds.width + EventLabel.PADDING
        }
    }

    private fun Row.getTooClose(): MutableList<MutableList<EventLabel>> {
        console.log("Grouping event labels in current row if they're too close")
        val tooClose = mutableListOf<MutableList<EventLabel>>()

        if (isEmpty()) {
            console.log("This row is empty. Stop grouping.")
            return tooClose
        }

        var groupStartIndex = 0
        while (groupStartIndex < size) {
            val groupEndIndex = getNextTooCloseGroup(groupStartIndex)
            tooClose += mutableSlice(groupStartIndex, groupEndIndex)
            groupStartIndex = groupEndIndex
        }
        console.log("End of row. Groups:")
        tooClose.forEach { console.log(it.toString()) }

        return tooClose
    }

    /**
     * @param startIndex the first index of the group (inclusive).
     * @return the last index of the group (exclusive).
     */
    private fun Row.getNextTooCloseGroup(startIndex: Int): Int {
        if (startIndex >= lastIndex) {
            return size
        }

        var prevExpandedXRange = this[startIndex].getExpandedXRange()

        var debugGroup = false
        val startStr = this[startIndex].toString()
        if (startStr in debugEventLabels) {
            debugGroup = true
            console.log("Group starts with '$startStr'")
        }

        for ((index, eventLabel) in withIndex().drop(startIndex + 1)) {
            val expandedXRange = eventLabel.getExpandedXRange()
            if (!expandedXRange.overlaps(prevExpandedXRange)) {
                if (debugGroup) {
                    console.log("'${eventLabel}' is not close enough" +
                            " to the previous label to be part of the group. Group ended.")
                    console.log("Group: ${subList(startIndex, index + 1)}")
                }
                return index
            }
            prevExpandedXRange = expandedXRange
            if (debugGroup) {
                console.log("'${eventLabel.textStr}' is close enough to the previous" +
                        " label to be added to the group")
                console.log("The group is now ${subList(startIndex, index + 1)}")
            }
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
