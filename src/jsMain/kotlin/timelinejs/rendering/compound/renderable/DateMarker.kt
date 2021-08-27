package timelinejs.rendering.compound.renderable

import timelinejs.rendering.Renderer
import timelinejs.datastructure.Point
import timelinejs.rendering.compound.RenderParent
import timelinejs.rendering.simple.renderable.Circle
import timelinejs.rendering.simple.renderable.Text
import timelinejs.rendering.compound.style.DateMarkerStyle

class DateMarker(
    location: Point,
    dateStr: String,
    style: DateMarkerStyle,
    renderer: Renderer
) : RenderParent() {
    private val circle = Circle(
        location,
        style.circleRadius,
        style.circleStyle,
        renderer
    )
    private val text = Text(
        location.translate(0.0, style.textOffset),
        dateStr,
        style.textStyle,
        renderer
    )

    init {
        children = mutableListOf(circle, text)
    }
}
