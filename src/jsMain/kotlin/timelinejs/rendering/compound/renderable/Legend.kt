package timelinejs.rendering.compound.renderable

import timelinejs.JsTimelineData
import timelinejs.datastructure.Rectangle
import timelinejs.rendering.Renderer
import timelinejs.rendering.compound.RenderParent

class Legend(
    private val data: JsTimelineData,
    private val bounds: Rectangle,
    private val renderer: Renderer
) : RenderParent() {
    init {
        TODO()
    }
}
