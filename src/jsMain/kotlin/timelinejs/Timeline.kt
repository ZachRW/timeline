package timelinejs

import timelinecommon.TimelineData
import org.w3c.dom.CanvasRenderingContext2D
import kotlin.js.Date

class Timeline(
    private val ctx: CanvasRenderingContext2D,
    private val data: TimelineData,
    private var dim: Vector2D
) {
    private var startDate: Date
    private var msPerPx: Double

    private val dateAxis: DateAxis

    init {
        val (start, end) = data.dateRange
        startDate = start
        msPerPx = (end.getTime() - start.getTime()) / dim.x

        dateAxis = DateAxis(dim.y / 2, dim.x)
    }

    fun zoom(zoomPx: Double, multiplier: Double) {
        val startMs = startDate.getTime()
        val zoomMs = zoomPx * msPerPx

        val startMsZoomed = zoomMs - multiplier * (zoomMs - startMs)

        startDate = Date(startMsZoomed)
        msPerPx *= multiplier

        update()
    }

    fun translate(deltaPx: Double) {
        startDate = Date(startDate.getTime() + deltaPx * msPerPx)

        update()
    }

    fun draw() {
        ctx.clearRect(0.0, 0.0, dim.x, dim.y)
        dateAxis.draw(ctx, startDate, msPerPx)
    }

    private fun update() {
        draw()
    }
}
