package timelinejs.rendering

import timelinejs.View
import timelinejs.datastructure.DynamicPoint
import timelinejs.rendering.compound.renderable.EventLabel

private const val DEFAULT_PX_FROM_AXIS = 100.0
private const val MIN_PX_FROM_AXIS = 10.0
private const val EVENT_LABEL_PADDING = 5.0

class EventLabelLayouter(
    eventLabels: List<EventLabel>,
    private val dateAxisY: Double,
    private val view: View
) {
    private val eventLabels = eventLabels.sortedBy { it.location.xDate.getTime() }

    fun layout() {
        moveToDefaultPositions()

        val intersecting = getIntersecting()
        align(intersecting)
    }

    private fun moveToDefaultPositions() {
        for (eventLabel in eventLabels) {
            val xDate = view.datePlusPx(eventLabel.date, -eventLabel.bounds.width / 2)
            val y = dateAxisY - DEFAULT_PX_FROM_AXIS - eventLabel.bounds.height

            eventLabel.location = DynamicPoint(xDate, y, view)
        }
    }

    private fun flipEventLabels(intersecting: List<EventLabel>) {
        TODO()
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

//        val offset = Aligner(labelCenterPxs, datePxs).getOffset()
        val offset = 0.0

        var eventLabelPx = offset - eventLabels[0].bounds.width / 2
        for (eventLabel in eventLabels) {
            eventLabel.location =
                eventLabel.location.copy(xDate = view.pxToDate(eventLabelPx))
            eventLabelPx += eventLabel.bounds.width + EVENT_LABEL_PADDING
        }
    }

    private fun getIntersecting(): List<EventLabel> {
        val intersecting = mutableListOf<EventLabel>()

        for (eventLabel1 in eventLabels) {
            for (eventLabel2 in eventLabels - intersecting) {
                if (eventLabel1.bounds.intersects(eventLabel2.bounds)) {
                    intersecting += eventLabel1
                    intersecting += eventLabel2
                }
            }
        }
        return intersecting
    }

    private fun EventLabel.isMoveValid(newLocation: DynamicPoint): Boolean {
        val newBounds = bounds.copy(location = newLocation)
        return (eventLabels - this).any {
            it.bounds.intersects(newBounds)
        }
    }
}
