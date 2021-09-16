package timelinejs.rendering.simple.renderable

import timelinejs.rendering.Renderer
import timelinejs.datastructure.Size
import timelinejs.datastructure.StaticPoint
import timelinejs.rendering.StaticRenderable
import timelinejs.rendering.simple.style.TextStyle
import timelinejs.rendering.compound.style.DrawMode

class StaticText(
    private val location: StaticPoint,
    private val text: String,
    private val style: TextStyle,
    private val renderer: Renderer
) : StaticRenderable {
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
