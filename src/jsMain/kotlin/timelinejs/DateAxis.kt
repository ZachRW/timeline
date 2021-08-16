package timelinejs

import org.w3c.dom.CENTER
import org.w3c.dom.CanvasTextAlign
import timelinejs.config.DateAxisConfig
import kotlin.js.Date
import org.w3c.dom.CanvasRenderingContext2D as RenderContext

class DateAxis(
    private val y: Double,
    private val renderContext: RenderContext,
    private val view: View,
    private val config: DateAxisConfig
) {
    fun draw() {
        drawLine()
        drawMarkers()
    }

    private fun drawLine() {
        applyLineConfig()
        renderContext.stroke {
            moveTo(0.0, y)
            lineTo(view.width, y)
        }
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
        renderContext.fillCircle(center, config.markerRadius)

        val textCenter = center + Vector2D(0, 20)
        applyYearTextConfig()
        renderContext.fillText(date.getFullYear().toString(), textCenter.x, textCenter.y)
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
        with(renderContext) {
            lineWidth = config.lineWidth
            strokeStyle = config.lineStyle
        }
    }

    private fun applyMarkerConfig() {
        renderContext.fillStyle = config.markerStyle
    }

    private fun applyYearTextConfig() {
        with(renderContext) {
            font = config.yearTextConfig.font
            fillStyle = config.yearTextConfig.color
            textAlign = CanvasTextAlign.CENTER
        }
    }
}
