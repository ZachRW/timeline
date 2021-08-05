package timelinejs

import timelinecommon.TimelineData
import org.w3c.dom.CanvasRenderingContext2D
import kotlin.js.Date

class Timeline(
    private val ctx: CanvasRenderingContext2D,
    private val data: TimelineData,
    private var dim: Vector2D
) {
    private val view: MutableView

    private val dateAxis: DateAxis

    init {
        val (start, end) = data.dateRange
        view = MutableView(start, end, dim.x)

        dateAxis = DateAxis(dim.y / 2, dim.x)
    }

    fun zoom(zoomPx: Double, multiplier: Double) {
        view.zoom(zoomPx, multiplier)
        update()
    }

    fun translate(deltaPx: Double) {
//        startDate += deltaPx * msPerPx
        TODO()
        update()
    }

    fun draw() {
        ctx.clearRect(0.0, 0.0, dim.x, dim.y)
//        dateAxis.draw(ctx, startDate, msPerPx)
        TODO()
    }

    private fun update() {
        draw()
    }
}
