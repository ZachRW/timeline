package timelinejs.rendering.simple.renderable

import timelinejs.datastructure.StaticPoint
import timelinejs.rendering.simple.style.LineStyle
import timelinejs.rendering.Renderer
import timelinejs.rendering.StaticRenderable

class StaticLine(
    private val point1: StaticPoint,
    private val point2: StaticPoint,
    private val style: LineStyle,
    private val renderer: Renderer
) : StaticRenderable {
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
