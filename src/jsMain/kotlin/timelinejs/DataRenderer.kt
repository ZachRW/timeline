package timelinejs

import org.w3c.dom.CanvasRenderingContext2D as RenderContext

class DataRenderer(
    private val data: JsTimelineData,
    private val renderContext: RenderContext,
    private val view: View
) {
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
        val nameMetrics = renderContext.measureText(name)
    }

    private fun JsNamedDateRange.draw() {
        // TODO
    }
}
