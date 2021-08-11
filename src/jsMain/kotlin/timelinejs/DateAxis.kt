package timelinejs

import kotlin.js.Date
import org.w3c.dom.CanvasRenderingContext2D as RenderContext

class DateAxis(
    private val y: Double,
    private val renderContext: RenderContext,
    private val view: View,
    private val config: DateAxisConfig = DateAxisConfig.DEFAULT
) {
    fun draw() {
        drawLine()
        drawMarkers()
    }

    private fun drawLine() =
        with(renderContext) {
            applyLineConfig(config)
            stroke {
                moveTo(0.0, y)
                lineTo(view.width, y)
            }
        }

    private fun drawMarkers() {
        val startRenderDate = view.pxToDate(-config.markerRadius)
        val endRenderDate = view.pxToDate(view.width + config.markerRadius)

        val markerDates = yearsWithin(startRenderDate..endRenderDate)
        renderContext.applyMarkerConfig(config)
        for (markerDate in markerDates) {
            drawMarker(markerDate)
        }
    }

    private fun drawMarker(date: Date) {
        val center = Vector2D(view.dateToPx(date), y)
        renderContext.fillCircle(center, config.markerRadius)
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
