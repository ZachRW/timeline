package timelinejs.rendering

import timelinejs.View
import timelinejs.datastructure.DynamicPoint
import timelinejs.rendering.compound.renderable.EventLabel

private const val MIN_DISTANCE_FROM_AXIS = 100.0
private const val EVENT_LABEL_PADDING = 5.0

class EventLabelLayouter(
    eventLabels: List<EventLabel>,
    private val dateAxisY: Double,
    private val view: View
) {
    private val eventLabels = eventLabels.sortedBy { it.location.xDate.getTime() }
    private val eventLabelsByRow = mutableMapOf<Int, MutableList<EventLabel>>()

    fun layout() {
        eventLabelsByRow.clear()
        moveToDefaultPositions()

        for (eventLabelRow in eventLabelsByRow.values) {
            do {
                val tooClose = eventLabelRow.getTooClose()
                tooClose.forEach(::align)
            } while (tooClose.isNotEmpty())
        }
    }

    private fun moveToDefaultPositions() {
        var top = true

        for (eventLabel in eventLabels) {
            eventLabel.location =
                DynamicPoint(view.datePlusPx(eventLabel.date, -eventLabel.bounds.width / 2), 0.0, view)

            eventLabel.moveToRow(if (top) 1 else -1)
            top = !top
        }
    }

    private fun EventLabel.moveToRow(row: Int) {
        if (row == 0) {
            error("Row 0 does not exist")
        }

        eventLabelsByRow.getOrPut(row) { mutableListOf() } += this

        val newY = if (row > 0) {
            dateAxisY - MIN_DISTANCE_FROM_AXIS - bounds.height -
                    (bounds.height + EVENT_LABEL_PADDING) * (row - 1)
        } else {
            dateAxisY + MIN_DISTANCE_FROM_AXIS +
                    (bounds.height + EVENT_LABEL_PADDING) * (-row - 1)
        }

        location = location.copy(y = newY)
    }

    private fun align(eventLabels: List<EventLabel>) {
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

    private fun EventLabel.isMoveValid(newLocation: DynamicPoint): Boolean {
        val newBounds = bounds.copy(location = newLocation)
        return (eventLabels - this).any {
            it.bounds.intersects(newBounds)
        }
    }
}

// FIXME - Skips EventLabels between groups
private fun List<EventLabel>.getTooClose(): List<List<EventLabel>> {
    val tooClose = mutableListOf<List<EventLabel>>()

    if (isEmpty()) {
        return tooClose
    }

    val iterator = iterator()
    while (iterator.hasNext()) {
        val group = iterator.getNextTooCloseGroup()
        if (group.isNotEmpty()) {
            tooClose += group
        }
    }

    return tooClose
}

private fun Iterator<EventLabel>.getNextTooCloseGroup(): List<EventLabel> {
    if (!hasNext()) {
        return emptyList()
    }

    val group = mutableListOf<EventLabel>()
    val firstEventLabel = next()
    group += firstEventLabel
    var prevExpandedBounds =
        firstEventLabel.bounds.grow(EVENT_LABEL_PADDING / 2, 0.0)
    while (hasNext()) {
        val eventLabel = next()
        val expandedBounds =
            eventLabel.bounds.grow(EVENT_LABEL_PADDING / 2, 0.0)

        if (expandedBounds.intersects(prevExpandedBounds)) {
            group += eventLabel
        } else {
            break
        }

        prevExpandedBounds = expandedBounds
    }

    return if (group.size == 1) {
        emptyList()
    } else {
        group
    }
}
