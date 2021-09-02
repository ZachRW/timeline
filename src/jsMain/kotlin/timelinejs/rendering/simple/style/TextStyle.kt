package timelinejs.rendering.simple.style

import org.w3c.dom.ALPHABETIC
import org.w3c.dom.CanvasTextAlign
import org.w3c.dom.CanvasTextBaseline
import org.w3c.dom.START
import timelinejs.rendering.Renderer
import timelinejs.rendering.compound.style.DrawMode

class TextStyle(
    private val jsStyle: dynamic = "black",
    private val font: String,
    val drawMode: DrawMode = DrawMode.FILL,
    private val textAlign: CanvasTextAlign = CanvasTextAlign.START,
    private val textBaseline: CanvasTextBaseline = CanvasTextBaseline.ALPHABETIC
) {
    fun applyStyle(renderer: Renderer) {
        renderer.font = font
        renderer.textAlign = textAlign
        renderer.textBaseline = textBaseline

        when (drawMode) {
            DrawMode.FILL -> renderer.fillStyle = jsStyle
            DrawMode.STROKE -> renderer.strokeStyle = jsStyle
        }
    }
}
