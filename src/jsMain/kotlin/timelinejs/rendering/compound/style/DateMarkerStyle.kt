package timelinejs.rendering.compound.style

import org.w3c.dom.CENTER
import org.w3c.dom.CanvasTextAlign
import timelinejs.rendering.simple.style.ClosedShapeStyle
import timelinejs.rendering.simple.style.TextStyle

class DateMarkerStyle(
    val circleRadius: Double,
    val circleStyle: ClosedShapeStyle,
    val textStyle: TextStyle,
    val textOffset: Double
) {
    companion object {
        val DEFAULT = DateMarkerStyle(
            circleRadius = 10.0,
            circleStyle = ClosedShapeStyle(
                jsStyle = "black",
                drawMode = DrawMode.FILL
            ),
            textStyle = TextStyle(
                jsStyle = "black",
                font = "10px",
                drawMode = DrawMode.FILL,
                textAlign = CanvasTextAlign.CENTER
            ),
            textOffset = 20.0
        )
    }
}
