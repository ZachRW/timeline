package timelinejs

import org.w3c.dom.CanvasRenderingContext2D as CanvasContext
import kotlin.js.Date

class DateAxis(private val y: Double) {
    fun draw(ctx: CanvasContext, view: View) {
        drawLine(ctx, view)
        drawMarkers(ctx, view, view.yearDatesWithin())
    }

    private fun drawLine(ctx: CanvasContext, view: View) {
        with(ctx) {
            lineWidth = 10.0
            strokeStyle = "black"

            stroke {
                moveTo(0.0, y)
                lineTo(view.width, y)
            }
        }
    }

    private fun drawMarkers(ctx: CanvasContext, view: View, dates: List<Date>) {
        for (date in dates) {
            with(ctx) {
                fillCircle(Vector2D(view.dateToPx(date), y), 10.0)
            }
        }
    }
}
