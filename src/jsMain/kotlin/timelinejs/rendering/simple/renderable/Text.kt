package timelinejs.rendering.simple.renderable

import timelinejs.rendering.Renderer
import timelinejs.datastructure.Size
import timelinejs.rendering.Renderable
import timelinejs.rendering.simple.style.TextStyle
import timelinejs.rendering.compound.style.DrawMode

class Text(
    private val location: Point,
    private val text: String,
    private val style: TextStyle,
    private val renderer: Renderer
) : Renderable {
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
