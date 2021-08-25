package timelinejs.rendering.renderable

import timelinejs.rendering.Renderer
import timelinejs.rendering.datastructure.Rectangle
import timelinejs.rendering.style.ClosedShapeStyle
import timelinejs.rendering.style.DrawMode
import timelinejs.rendering.style.RoundRectangleStyle

class RoundRectangle(
    private val bounds: Rectangle,
    private val style: RoundRectangleStyle,
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
            roundRect(bounds, style.radius)
        }
    }
}