package timelinejs.rendering.compound.renderable

import timelinecommon.TimelineData
import timelinejs.JsTimelineData
import timelinejs.MutableView
import timelinejs.rendering.compound.style.TimelineStyle
import timelinejs.rendering.Renderer
import timelinejs.datastructure.Rectangle
import timelinejs.rendering.compound.RenderParent
import timelinejs.toJsTimelineData
import org.w3c.dom.CanvasRenderingContext2D as RenderContext

class Timeline(
    renderContext: RenderContext,
    commonData: TimelineData,
    bounds: Rectangle,
    style: TimelineStyle = TimelineStyle.DEFAULT
) : RenderParent() {
    private val renderer = Renderer(renderContext, bounds)
    private val data: JsTimelineData = commonData.toJsTimelineData(style.seriesColorPalette)
    private val view: MutableView
    private val dateAxis: DateAxis

    init {
        val (start, end) = data.dateRange
        view = MutableView(start, end, bounds.width)
        dateAxis = DateAxis(bounds.centerY, renderer, view, style.dateAxisStyle)

        children += dateAxis
    }

    fun zoom(zoomPx: Double, multiplier: Double) {
        view.zoom(zoomPx, multiplier)
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
