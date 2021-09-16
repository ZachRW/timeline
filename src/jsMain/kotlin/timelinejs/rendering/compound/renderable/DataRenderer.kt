package timelinejs.rendering.compound.renderable

import timelinejs.*
import timelinejs.datastructure.DynamicPoint
import timelinejs.rendering.Renderable
import timelinejs.rendering.Renderer
import timelinejs.rendering.compound.StaticRenderParent
import timelinejs.rendering.compound.style.EventLabelStyle
import kotlin.js.Date

class DataRenderer(
    data: JsTimelineData,
    private val dateAxisY: Double,
    private val view: View,
    private val renderer: Renderer
) : Renderable {
    private val seriesViews: List<SeriesView> = data.toSeriesViews()

    override fun render() {
        for (seriesView in seriesViews) {
            if (seriesView.visible) {
                seriesView.render()
            }
        }
    }

    private fun JsTimelineData.toSeriesViews(): List<SeriesView> {
        val colorProvider = SeriesColorProvider(SeriesColorPalette.DEFAULT)
        return seriesList.map { jsSeries ->
            val eventLabelStyle = EventLabelStyle.defaultWithColor(colorProvider.next())
            SeriesView(jsSeries, eventLabelStyle)
        }
    }

    private inner class SeriesView(jsSeries: JsSeries, eventLabelStyle: EventLabelStyle) : StaticRenderParent() {
        var visible = true

        init {
            val eventLabels = jsSeries.events.map { jsEvent ->
                EventLabel(
                    textStr = jsEvent.name,
                    stemBaseY = dateAxisY,
                    style = eventLabelStyle,
                    renderer,
                    initLocation = DynamicPoint(Date(), 0.0, view)
                )
            }

            children.addAll(eventLabels)
        }
    }
}
