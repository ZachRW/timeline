package timelinejs.rendering.renderable

import timelinejs.rendering.Renderer
import timelinejs.rendering.style.RoundRectangleStyle
import timelinejs.rendering.datastructure.Rectangle
import timelinejs.rendering.style.DrawMode

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
            lineDash = style.lineDash.toTypedArray()

            when (style.drawMode) {
                DrawMode.FILL -> fillStyle = style.jsStyle
                DrawMode.STROKE -> strokeStyle = style.jsStyle
            }
        }
    }

    private fun fillOrStroke() {
        val drawFun = renderer.getDrawFun(style.drawMode)
        drawFun {
            roundRect(bounds, style.radius)
        }
    }
}