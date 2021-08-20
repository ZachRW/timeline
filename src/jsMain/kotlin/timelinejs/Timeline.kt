package timelinejs

import timelinecommon.TimelineData
import timelinejs.config.TimelineConfig
import timelinejs.rendering.Rectangle
import timelinejs.rendering.Renderer
import org.w3c.dom.CanvasRenderingContext2D as RenderContext

class Timeline(
    renderContext: RenderContext,
    commonData: TimelineData,
    private var bounds: Rectangle,
    config: TimelineConfig = TimelineConfig.DEFAULT
) {
    private val renderer = Renderer(renderContext, bounds)
    private val data: JsTimelineData = commonData.toJsTimelineData(config.seriesColorPalette)
    private val view: MutableView
    private val dateAxis: DateAxis
    private val dataRenderer: DataRenderer

    init {
        val (start, end) = data.dateRange
        view = MutableView(start, end, bounds.width)
        dateAxis = DateAxis(bounds.centerY, renderer, view, config.dateAxisConfig)
        dataRenderer = DataRenderer(data, renderer, view)
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
        renderer.clear()
        dateAxis.draw()
        dataRenderer.draw()
    }
}
