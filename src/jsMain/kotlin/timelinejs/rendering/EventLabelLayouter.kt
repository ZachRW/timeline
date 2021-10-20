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
        moveToDefaultPositions()

        eventLabelsByRow.values.forEach(::align)
    }

    private fun moveToDefaultPositions() {
        var top = true

        for (eventLabel in eventLabels) {
            eventLabel.moveToRow(if (top) 1 else -1)
            top = !top
        }
    }

    private fun EventLabel.moveToRow(row: Int) {
        if (row == 0) {
            error("Row 0 does not exist")
        }

        val rowList = eventLabelsByRow.getOrPut(row) { mutableListOf() }
        if (this !in rowList) {
            rowList += this
        }

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

    private fun getIntersecting(): List<EventLabel> {
        TODO("fix returning duplicates")

//        val intersecting = mutableListOf<EventLabel>()
//
//        for (eventLabel1 in eventLabels) {
//            for (eventLabel2 in eventLabels - intersecting) {
//                if (eventLabel1.bounds.intersects(eventLabel2.bounds)) {
//                    intersecting += eventLabel1
//                    intersecting += eventLabel2
//                }
//            }
//        }
//        return intersecting
    }

    private fun EventLabel.isMoveValid(newLocation: DynamicPoint): Boolean {
        val newBounds = bounds.copy(location = newLocation)
        return (eventLabels - this).any {
            it.bounds.intersects(newBounds)
        }
    }
}
