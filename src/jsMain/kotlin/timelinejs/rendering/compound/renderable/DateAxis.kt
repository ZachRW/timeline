package timelinejs.rendering.compound.renderable

import timelinejs.DateRange
import timelinejs.View
import timelinejs.fromYear
import timelinejs.rangeTo
import timelinejs.rendering.compound.style.DateAxisStyle
import timelinejs.rendering.Renderer
import timelinejs.datastructure.AbsolutePoint
import timelinejs.rendering.compound.RenderParent
import timelinejs.rendering.simple.renderable.Line
import kotlin.js.Date

class DateAxis(
    val y: Double,
    private val style: DateAxisStyle,
    private val view: View,
    private val renderer: Renderer
) : RenderParent() {
    private val axisLine = Line(
        AbsolutePoint(0.0, y),
        AbsolutePoint(view.width, y),
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
            AbsolutePoint(view.dateToPx(date), y),
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
