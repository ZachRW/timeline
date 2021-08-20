package timelinejs.rendering

import org.w3c.dom.CanvasTextAlign
import org.w3c.dom.CanvasTextBaseline

data class TextStyle(
    val style: dynamic,
    val font: String,
    val drawMode: DrawMode,
    val textAlign: CanvasTextAlign,
    val textBaseline: CanvasTextBaseline
)
