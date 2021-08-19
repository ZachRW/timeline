package timelinejs

import org.w3c.dom.CENTER
import org.w3c.dom.CanvasTextAlign
import timelinejs.config.DateAxisConfig
import kotlin.js.Date

class DateAxis(
    private val y: Double,
    private val renderer: Renderer,
    private val view: View,
    private val config: DateAxisConfig
) {
    fun draw() {
        drawLine()
        drawMarkers()
    }

    private fun drawLine() {
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
        val center = Vector2D(view.dateToPx(date), y)
        renderer.fillCircle(center, config.markerRadius)

        val textCenter = center + Vector2D(0, 20)
        applyYearTextConfig()
        renderer.fillText(date.getFullYear().toString(), textCenter.x, textCenter.y)
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
