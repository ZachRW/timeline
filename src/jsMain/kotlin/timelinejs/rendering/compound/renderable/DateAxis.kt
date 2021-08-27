package timelinejs.rendering.compound.renderable

import timelinejs.DateRange
import timelinejs.View
import timelinejs.fromYear
import timelinejs.rangeTo
import timelinejs.rendering.compound.style.DateAxisStyle
import timelinejs.rendering.Renderer
import timelinejs.datastructure.Point
import timelinejs.rendering.compound.RenderParent
import timelinejs.rendering.simple.renderable.Line
import kotlin.js.Date

class DateAxis(
    private val y: Double,
    private val renderer: Renderer,
    private val view: View,
    private val style: DateAxisStyle
) : RenderParent() {
    private val axisLine = Line(
        Point(0.0, y),
        Point(view.width, y),
        style.axisLineStyle,
        renderer
    )

    override fun render() {
        setChildren()
        renderChildren()
    }

    private fun setChildren() {
        children = mutableListOf(axisLine)
        addDateMarkersToChildren()
    }

    private fun addDateMarkersToChildren() {
        for (markerDate in getDateMarkerDates()) {
            addDateMarkerToChildren(markerDate)
        }
    }

    private fun addDateMarkerToChildren(date: Date) {
        children += DateMarker(
            Point(view.dateToPx(date), y),
            date.getFullYear().toString(),
            style.markerStyle,
            renderer
        )
    }

    private fun getDateMarkerDates(): List<Date> {
        val markerRadius = style.markerStyle.circleRadius
        val startRenderDate = view.pxToDate(-markerRadius)
        val endRenderDate = view.pxToDate(view.width + markerRadius)

        return yearsWithin(startRenderDate..endRenderDate)
    }

    private fun yearsWithin(dateRange: DateRange): List<Date> {
        val (startDate, endDate) = dateRange
        var startYear = startDate.getFullYear()
        val endYear = endDate.getFullYear()

        if (Date.fromYear(startYear) in dateRange) {
            startYear++
        }

        return (startYear..endYear).map(Date::fromYear)
    }
}
