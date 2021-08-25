package timelinejs.rendering.style

import org.w3c.dom.ALPHABETIC
import org.w3c.dom.CanvasTextAlign
import org.w3c.dom.CanvasTextBaseline
import org.w3c.dom.START

data class TextStyle(
    val jsStyle: dynamic = "black",
    val font: String,
    val drawMode: DrawMode = DrawMode.FILL,
    val textAlign: CanvasTextAlign = CanvasTextAlign.START,
    val textBaseline: CanvasTextBaseline = CanvasTextBaseline.ALPHABETIC
)
