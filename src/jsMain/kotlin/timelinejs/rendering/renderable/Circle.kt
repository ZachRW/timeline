package timelinejs.rendering.renderable

import timelinejs.rendering.Renderer
import timelinejs.rendering.datastructure.Point
import timelinejs.rendering.style.ClosedShapeStyle
import timelinejs.rendering.style.DrawMode

class Circle(
    private val center: Point,
    private val radius: Double,
    private val style: ClosedShapeStyle,
    private val renderer: Renderer
) : Renderable {
    override fun render() {
        applyStyle()
        fillOrStroke()
    }

    private fun applyStyle() {
        with(renderer) {
            lineWidth = style.lineWidth
            lineDash = style.lineDash

            when (style.drawMode) {
                DrawMode.FILL -> fillStyle = style.jsStyle
                DrawMode.STROKE -> strokeStyle = style.jsStyle
            }
        }
    }

    private fun fillOrStroke() {
        renderer.useDrawFun(style.drawMode) {
            circle(center, radius)
        }
    }
}
