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

    init {
        addChildren(axisLine)
    }

    override fun render() {
        renderChildren()
        createDateMarkers().forEach(DateMarker::render)
    }

    private fun createDateMarkers(): List<DateMarker> {
        val markers = mutableListOf<DateMarker>()

        val markerRadius = style.markerStyle.circleRadius
        val startRenderDate = view.pxToDate(-markerRadius)
        val endRenderDate = view.pxToDate(view.width + markerRadius)

        val markerDates = yearsWithin(startRenderDate..endRenderDate)
        for (markerDate in markerDates) {
            markers += createDateMarker(markerDate)
        }

        return markers
    }

    private fun createDateMarker(date: Date): DateMarker {
        return DateMarker(
            Point(view.dateToPx(date), y),
            date.getFullYear().toString(),
            style.markerStyle,
            renderer
        )
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
