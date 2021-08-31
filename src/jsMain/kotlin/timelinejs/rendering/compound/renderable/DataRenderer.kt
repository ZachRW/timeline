package timelinejs.rendering.compound.renderable

import timelinejs.*
import timelinejs.rendering.Renderer
import timelinejs.rendering.compound.RenderParent

class DataRenderer(
    private val data: JsTimelineData,
    private val renderer: Renderer
) : RenderParent() {
    init {
        initSeriesList()
    }

    private fun initSeriesList() {
        for (series in data.seriesList) {
            initSeries(series)
        }
    }

    private fun initSeries(series: JsSeries) {
        for (event in series.events) {
            createEventLabel(event, series.color)
        }
        for (namedDateRange in series.namedDateRanges) {
            createDateRangeLabel(namedDateRange, series.color)
        }
    }

    private fun createEventLabel(event: JsEvent, color: String) {
        TODO()
    }

    private fun createDateRangeLabel(namedDateRange: JsNamedDateRange, color: String) {
        // TODO
    }
}
