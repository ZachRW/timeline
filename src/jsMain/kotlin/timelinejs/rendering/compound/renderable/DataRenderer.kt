package timelinejs.rendering.compound.renderable

import timelinejs.*
import timelinejs.rendering.EventLabelLayouter
import timelinejs.rendering.Renderable
import timelinejs.rendering.Renderer
import timelinejs.rendering.compound.DynamicRenderParent
import timelinejs.rendering.compound.style.EventLabelStyle

class DataRenderer(
    data: JsTimelineData,
    private val dateAxisY: Double,
    private val view: View,
    private val renderer: Renderer
) : Renderable {
    private val seriesViews: List<SeriesView> = data.toSeriesViews()
    private val eventLabels: List<EventLabel>
        get() = seriesViews.flatMap { it.eventLabels }
    private val layouter = EventLabelLayouter(eventLabels, view)

    init {
        update()
    }

    override fun render() {
        for (seriesView in seriesViews) {
            if (seriesView.visible) {
                seriesView.render()
            }
        }
    }

    fun update() {
        layouter.layout()
    }

    private fun JsTimelineData.toSeriesViews(): List<SeriesView> {
        val colorProvider = SeriesColorProvider(SeriesColorPalette.DEFAULT)
        return seriesList.map { jsSeries ->
            val eventLabelStyle = EventLabelStyle.defaultWithColor(colorProvider.next())
            SeriesView(jsSeries, eventLabelStyle, dateAxisY, view, renderer)
        }
    }
}

private class SeriesView(
    jsSeries: JsSeries,
    eventLabelStyle: EventLabelStyle,
    dateAxisY: Double,
    view: View,
    renderer: Renderer
) : DynamicRenderParent(view) {
    var visible = true
    val eventLabels: List<EventLabel>

    init {
        eventLabels = jsSeries.events.map { jsEvent ->
            EventLabel(
                textStr = jsEvent.name,
                date = jsEvent.date,
                dateAxisY = dateAxisY,
                style = eventLabelStyle,
                renderer = renderer,
                view = view
            )
        }

        children.addAll(eventLabels)
    }
}
