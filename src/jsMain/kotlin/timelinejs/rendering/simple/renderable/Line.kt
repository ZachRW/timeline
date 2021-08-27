package timelinejs.rendering.simple.renderable

import timelinejs.rendering.simple.style.LineStyle
import timelinejs.rendering.Renderer
import timelinejs.datastructure.Point
import timelinejs.rendering.Renderable

class Line(
    private val point1: Point,
    private val point2: Point,
    private val style: LineStyle,
    private val renderer: Renderer
) : Renderable {
    override fun render() {
        applyStyle()
        renderer.line(point1, point2)
    }

    private fun applyStyle() {
        with(renderer) {
            strokeStyle = style.jsStyle
            lineWidth = style.width
            lineCap = style.cap
            lineDash = style.dash
        }
    }
}