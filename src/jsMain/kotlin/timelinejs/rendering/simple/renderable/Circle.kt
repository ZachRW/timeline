package timelinejs.rendering.simple.renderable

import timelinejs.rendering.Renderer
import timelinejs.datastructure.StaticPoint
import timelinejs.rendering.Renderable
import timelinejs.rendering.StaticRenderable
import timelinejs.rendering.compound.style.DrawMode
import timelinejs.rendering.simple.style.ClosedShapeStyle

class Circle(
    private val center: StaticPoint,
    private val radius: Double,
    private val style: ClosedShapeStyle,
    private val renderer: Renderer
) : StaticRenderable {
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
