package timelinejs.rendering.compound.renderable

import timelinejs.JsTimelineData
import timelinejs.datastructure.AbsoluteRectangle
import timelinejs.rendering.Renderer
import timelinejs.rendering.compound.RenderParent

class Legend(
    private val data: JsTimelineData,
    private val bounds: AbsoluteRectangle,
    private val renderer: Renderer
) : RenderParent() {
    init {
        TODO()
    }
}
