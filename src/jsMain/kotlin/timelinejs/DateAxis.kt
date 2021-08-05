package timelinejs

import org.w3c.dom.CanvasRenderingContext2D
import kotlin.js.Date

class DateAxis(private val y: Double, private val length: Double) {
    fun draw(ctx: CanvasRenderingContext2D, startDate: Date, msPerPx: Double) {
        val startMs = startDate.getTime()
        val endMs = startMs + msPerPx * length
        val endDate = Date(endMs)

        drawLine(ctx)
        drawMarkers()
    }

    fun drawLine(ctx: CanvasRenderingContext2D) {
        with(ctx) {
            lineWidth = 10.0
            strokeStyle = "black"

            stroke {
                moveTo(0.0, y)
                lineTo(length, y)
            }
        }
    }

    fun drawMarkers(
        ctx: CanvasRenderingContext2D,
        startDate: Date,
        msPerPx: Double,
        dates: List<Date>
    ) {
        for (yearDate in yearDatesWithin(startDate..endDate)) {
            with(ctx) {
                fill {

                }
            }
        }
    }
}
