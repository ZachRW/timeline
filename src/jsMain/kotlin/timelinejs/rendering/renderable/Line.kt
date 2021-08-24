package timelinejs.rendering.renderable

import timelinejs.rendering.style.LineStyle
import timelinejs.rendering.Renderer

class Line(
    private val x1: Double,
    private val y1: Double,
    private val x2: Double,
    private val y2: Double,
    private val style: LineStyle,
    private val renderer: Renderer
) : Renderable {
    override fun render() {
        applyStyle()
        renderer.line(x1, y1, x2, y2)
    }

    private fun applyStyle() {
        with(renderer) {
            strokeStyle = style.jsStyle
            lineWidth = style.width
            lineCap = style.cap
            lineDash = style.dash.toTypedArray()
        }
    }
}