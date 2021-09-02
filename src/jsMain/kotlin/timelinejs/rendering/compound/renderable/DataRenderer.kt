package timelinejs.rendering.compound.renderable

import timelinejs.*
import timelinejs.rendering.Renderer
import timelinejs.rendering.compound.RenderParent

class DataRenderer private constructor(
    private val eventLabels: List<EventLabel>,
    private val renderer: Renderer
) : RenderParent() {
    init {
        children.addAll(eventLabels)
    }

    companion object {
        fun create(data: JsTimelineData, renderer: Renderer) =
            DataRendererBuilder(data, renderer).build()

        private class DataRendererBuilder(
            private val data: JsTimelineData,
            private val renderer: Renderer
        ) {
            fun build(): DataRenderer {
            }
        }
    }
}
