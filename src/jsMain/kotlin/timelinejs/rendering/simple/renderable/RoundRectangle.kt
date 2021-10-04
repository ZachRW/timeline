package timelinejs.rendering.simple.renderable

import timelinejs.View
import timelinejs.datastructure.DynamicRectangle
import timelinejs.rendering.Renderer
import timelinejs.datastructure.StaticRectangle
import timelinejs.rendering.DynamicRenderable
import timelinejs.rendering.Renderable
import timelinejs.rendering.compound.style.DrawMode
import timelinejs.rendering.compound.style.RoundRectangleStyle

class RoundRectangle(
    bounds: DynamicRectangle,
    private val style: RoundRectangleStyle,
    private val renderer: Renderer,
    view: View
) : DynamicRenderable(view) {
    var bounds: DynamicRectangle = bounds
        private set
    var location
        get() = bounds.location
        set(value) {
            bounds = bounds.copy(location = value)
        }

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
            roundRect(bounds.toStaticRectangle(), style.radius)
        }
    }
}
