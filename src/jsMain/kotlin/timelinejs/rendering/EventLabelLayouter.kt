package timelinejs.rendering

import timelinejs.View
import timelinejs.datastructure.DynamicPoint
import timelinejs.rendering.compound.renderable.EventLabel

private val DEFAULT_PX_FROM_AXIS = 100.0
private val MIN_PX_FROM_AXIS = 10.0

class EventLabelLayouter(
    private val eventLabels: List<EventLabel>,
    private val dateAxisY: Double,
    private val view: View
) {
    fun layout() {
        moveToDefaultPositions()
    }

    private fun moveToDefaultPositions() {
        for (eventLabel in eventLabels) {
            val xDate = view.datePlusPx(eventLabel.date, eventLabel.bounds.width / 2)
            val y = dateAxisY - DEFAULT_PX_FROM_AXIS - eventLabel.bounds.height

            eventLabel.location = DynamicPoint(xDate, y, view)
        }
    }
}
