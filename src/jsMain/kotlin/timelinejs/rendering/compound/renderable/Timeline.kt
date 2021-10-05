package timelinejs.rendering.compound.renderable

import timelinecommon.TimelineData
import timelinejs.JsTimelineData
import timelinejs.MutableView
import timelinejs.rendering.compound.style.TimelineStyle
import timelinejs.rendering.Renderer
import timelinejs.datastructure.StaticRectangle
import timelinejs.rendering.compound.StaticRenderParent
import timelinejs.toJsTimelineData
import org.w3c.dom.CanvasRenderingContext2D as RenderContext

class Timeline(
    renderContext: RenderContext,
    commonData: TimelineData,
    bounds: StaticRectangle,
    style: TimelineStyle = TimelineStyle.DEFAULT
) : StaticRenderParent() {
    private val renderer: Renderer
    private val data: JsTimelineData
    private val view: MutableView
    private val dateAxis: DateAxis
    private val dataRenderer: DataRenderer

    init {
        renderer = Renderer(renderContext, bounds)
        data = commonData.toJsTimelineData()

        val (start, end) = data.dateRange
        view = MutableView(start, end, bounds.width)
        dateAxis = DateAxis(bounds.centerY, style.dateAxisStyle, view, renderer)

        dataRenderer = DataRenderer(data, dateAxis.y, view, renderer)

        children += dataRenderer
        children += dateAxis
    }

    fun zoom(zoomPx: Double, multiplier: Double) {
        view.zoom(zoomPx, multiplier)
        dataRenderer.update()
        render()
    }

    fun translate(deltaPx: Double) {
        view.translate(deltaPx)
        render()
    }

    override fun render() {
        renderer.clear()
        renderChildren()
    }
}
