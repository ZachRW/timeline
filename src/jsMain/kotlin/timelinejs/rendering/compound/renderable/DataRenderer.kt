package timelinejs.rendering.compound.renderable

import timelinejs.*
import timelinejs.rendering.Renderer
import timelinejs.rendering.compound.RenderParent

class DataRenderer(
    private val data: JsTimelineData,
    private val renderContext: Renderer,
    private val view: View
) : RenderParent() {
    init {
        TODO()
    }

    fun draw() {
        for (series in data.seriesList) {
            if (series.visible) {
                series.draw()
            }
        }
    }

    private fun JsSeries.draw() {
        for (event in events) {
            event.draw()
        }
        for (namedDateRange in namedDateRanges) {
            namedDateRange.draw()
        }
    }

    private fun JsEvent.draw() {
    }

    private fun JsNamedDateRange.draw() {
        // TODO
    }
}
