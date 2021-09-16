package timelinejs.rendering.simple.renderable

import timelinejs.View
import timelinejs.datastructure.DynamicPoint
import timelinejs.rendering.DynamicRenderable
import timelinejs.rendering.simple.style.LineStyle
import timelinejs.rendering.Renderer
import timelinejs.rendering.Renderable

class DynamicLine(
    private val point1: DynamicPoint,
    private val point2: DynamicPoint,
    private val style: LineStyle,
    private val renderer: Renderer,
    view: View
) : DynamicRenderable(view) {
    override fun render() {
        applyStyle()
        renderer.line(point1.toStaticPoint(), point2.toStaticPoint())
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
