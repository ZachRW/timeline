package timelinejs.rendering.compound.renderable

import timelinejs.rendering.Renderer
import timelinejs.datastructure.StaticPoint
import timelinejs.rendering.compound.StaticRenderParent
import timelinejs.rendering.simple.renderable.Circle
import timelinejs.rendering.simple.renderable.StaticText
import timelinejs.rendering.compound.style.DateMarkerStyle

class DateMarker(
    location: StaticPoint,
    dateStr: String,
    style: DateMarkerStyle,
    renderer: Renderer
) : StaticRenderParent() {
    private val circle = Circle(
        location,
        style.circleRadius,
        style.circleStyle,
        renderer
    )
    private val text = StaticText(
        location.translate(0.0, style.textOffset),
        dateStr,
        style.textStyle,
        renderer
    )

    init {
        children = mutableListOf(circle, text)
    }
}
