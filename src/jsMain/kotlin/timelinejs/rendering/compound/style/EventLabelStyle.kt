package timelinejs.rendering.compound.style

import org.w3c.dom.CanvasTextBaseline
import org.w3c.dom.TOP
import timelinejs.rendering.simple.style.LineStyle
import timelinejs.rendering.simple.style.TextStyle

class EventLabelStyle(
    val enclosedTextStyle: EnclosedTextStyle,
    val stemStyle: LineStyle
) {
    companion object {
        fun defaultWithColor(color: String) = EventLabelStyle(
            EnclosedTextStyle(
                RoundRectangleStyle(
                    radius = 5.0,
                    jsStyle = color,
                    drawMode = DrawMode.FILL,
                ),
                TextStyle(
                    jsStyle = "black",
                    font = "12px sans-serif",
                    textBaseline = CanvasTextBaseline.TOP
                ),
                textPadding = 6.0
            ),
            LineStyle(
                jsStyle = color,
                width = 4.0
            )
        )
    }
}
