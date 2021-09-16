package timelinejs.rendering.simple.renderable

import timelinejs.View
import timelinejs.datastructure.DynamicPoint
import timelinejs.rendering.Renderer
import timelinejs.datastructure.Size
import timelinejs.rendering.DynamicRenderable
import timelinejs.rendering.simple.style.TextStyle
import timelinejs.rendering.compound.style.DrawMode

class DynamicText(
    private val location: DynamicPoint,
    private val text: String,
    private val style: TextStyle,
    private val renderer: Renderer,
    view: View
) : DynamicRenderable(view) {
    val size: Size

    init {
        style.applyStyle(renderer)
        size = renderer.textSize(text)
    }

    override fun render() {
        style.applyStyle(renderer)
        fillOrStroke()
    }

    private fun fillOrStroke() {
        when (style.drawMode) {
            DrawMode.FILL ->
                renderer.fillText(text, location)
            DrawMode.STROKE ->
                renderer.strokeText(text, location)
        }
    }
}
