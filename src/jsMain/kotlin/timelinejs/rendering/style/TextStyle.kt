package timelinejs.rendering.style

import org.w3c.dom.CanvasTextAlign
import org.w3c.dom.CanvasTextBaseline

data class TextStyle(
    val jsStyle: dynamic,
    val font: String,
    val drawMode: DrawMode,
    val textAlign: CanvasTextAlign,
    val textBaseline: CanvasTextBaseline
)
