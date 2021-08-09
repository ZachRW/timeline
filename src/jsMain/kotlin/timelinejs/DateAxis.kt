package timelinejs

import org.w3c.dom.CanvasRenderingContext2D as RenderContext
import kotlin.js.Date

class DateAxis(
    private val y: Double,
    private val renderContext: RenderContext,
    private val view: View
) {
    fun draw() {
        drawLine()
        drawMarkers(view.yearDatesWithin())
    }

    private fun drawLine() {
        with(renderContext) {
            lineWidth = 2.0
            strokeStyle = "black"

            stroke {
                moveTo(0.0, y)
                lineTo(view.width, y)
            }
        }
    }

    private fun drawMarkers(dates: List<Date>) {
        renderContext.fillStyle = "black"
        for (date in dates) {
            val position = Vector2D(view.dateToPx(date), y)
            console.log("Drawing at $position")
            renderContext.fillCircle(position, 10.0)
        }
    }
}
