package timelinejs.rendering.compound.renderable

import timelinejs.JsTimelineData
import timelinejs.datastructure.StaticRectangle
import timelinejs.rendering.Renderer
import timelinejs.rendering.compound.StaticRenderParent

class Legend(
    private val data: JsTimelineData,
    private val bounds: StaticRectangle,
    private val renderer: Renderer
) : StaticRenderParent() {
    init {
        TODO()
    }
}
