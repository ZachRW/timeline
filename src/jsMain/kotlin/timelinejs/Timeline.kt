package timelinejs

import timelinecommon.TimelineData
import org.w3c.dom.CanvasRenderingContext2D as RenderContext

class Timeline(
    private val renderContext: RenderContext,
    commonData: TimelineData,
    private var dim: Vector2D
) {
    private val data: JsTimelineData = commonData.toJsTimelineData()
    private val view: MutableView
    private val dateAxis: DateAxis
    private val dataRenderer: DataRenderer

    init {
        val (start, end) = data.dateRange
        view = MutableView(start, end, dim.x)
        dateAxis = DateAxis(dim.y / 2, renderContext, view)
        dataRenderer = DataRenderer(data, renderContext, view)
    }

    fun zoom(zoomPx: Double, multiplier: Double) {
        view.zoom(zoomPx, multiplier)
        draw()
    }

    fun translate(deltaPx: Double) {
        view.translate(deltaPx)
        draw()
    }

    fun draw() {
        clearCanvas()
        dateAxis.draw()
        dataRenderer.draw()
    }

    private fun clearCanvas() =
        renderContext.clearRect(0.0, 0.0, dim.x, dim.y)
}
