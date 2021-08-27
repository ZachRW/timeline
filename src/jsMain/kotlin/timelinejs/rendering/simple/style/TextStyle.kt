package timelinejs.rendering.simple.style

import org.w3c.dom.ALPHABETIC
import org.w3c.dom.CanvasTextAlign
import org.w3c.dom.CanvasTextBaseline
import org.w3c.dom.START
import timelinejs.rendering.compound.style.DrawMode

class TextStyle(
    val jsStyle: dynamic = "black",
    val font: String,
    val drawMode: DrawMode = DrawMode.FILL,
    val textAlign: CanvasTextAlign = CanvasTextAlign.START,
    val textBaseline: CanvasTextBaseline = CanvasTextBaseline.ALPHABETIC
)
