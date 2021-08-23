package timelinejs

import org.w3c.dom.CENTER
import org.w3c.dom.CanvasTextAlign
import timelinejs.config.DateAxisConfig
import timelinejs.rendering.Renderer
import timelinejs.rendering.datastructures.Point
import kotlin.js.Date

class DateAxis(
    private val y: Double,
    private val renderer: Renderer,
    private val view: View,
    private val config: DateAxisConfig
) {
    fun draw() {
        drawAxisLine()
        drawMarkers()
    }

    private fun drawAxisLine() {
        applyLineConfig()
        renderer.line(0.0, y, view.width, y)
    }

    private fun drawMarkers() {
        val startRenderDate = view.pxToDate(-config.markerRadius)
        val endRenderDate = view.pxToDate(view.width + config.markerRadius)

        val markerDates = yearsWithin(startRenderDate..endRenderDate)
        applyMarkerConfig()
        for (markerDate in markerDates) {
            drawMarker(markerDate)
        }
    }

    private fun drawMarker(date: Date) {
        val center = Point(view.dateToPx(date), y)
        renderer.fillCircle(center, config.markerRadius)

        val textCenter = center + Point(0.0, 20.0)
        applyYearTextConfig()
        renderer.fillText(date.getFullYear().toString(), textCenter)
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

    private fun applyLineConfig() {
        with(renderer) {
            lineWidth = config.lineWidth
            strokeStyle = config.lineStyle
        }
    }

    private fun applyMarkerConfig() {
        renderer.fillStyle = config.markerStyle
    }

    private fun applyYearTextConfig() {
        with(renderer) {
            font = config.yearTextConfig.font
            fillStyle = config.yearTextConfig.color
            textAlign = CanvasTextAlign.CENTER
        }
    }
}
