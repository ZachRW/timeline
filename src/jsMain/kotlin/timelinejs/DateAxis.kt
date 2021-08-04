package timelinejs

import org.w3c.dom.CanvasRenderingContext2D
import kotlin.js.Date

class DateAxis(private val y: Double, private val length: Double) {
    fun draw(ctx: CanvasRenderingContext2D, startDate: Date, msPerPx: Double) {
        val startMs = startDate.getTime()

        with(ctx) {
            beginPath()
            moveTo(0.0, y)
            lineTo(length, y)
            lineWidth = 10.0
            strokeStyle = "black"
            stroke()
        }
    }
}
