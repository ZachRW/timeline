package timelinejs.rendering.compound.renderable

import timelinejs.*
import timelinejs.rendering.Renderer
import timelinejs.rendering.compound.RenderParent

class DataRenderer private constructor(
    private val data: JsTimelineData,
    private val view: View,
    private val renderer: Renderer
) : RenderParent() {
    private val seriesViews: List<SeriesView> = data.toSeriesViews()

    private fun JsTimelineData.toSeriesViews(): List<SeriesView> {
    }

    private class SeriesView(val eventViews: List<EventView>, val color: String) {
        var visible = true
    }

    private class EventView(val seriesView: SeriesView, val label: EventLabel)
}
