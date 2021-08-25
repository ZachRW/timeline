package timelinejs.config

import org.w3c.dom.CENTER
import org.w3c.dom.CanvasTextAlign
import timelinejs.rendering.style.ClosedShapeStyle
import timelinejs.rendering.style.DrawMode
import timelinejs.rendering.style.LineStyle
import timelinejs.rendering.style.TextStyle

data class DateAxisConfig(
    val axisLineStyle: LineStyle,
    val markerRadius: Double,
    val markerStyle: ClosedShapeStyle,
    val yearTextStyle: TextStyle
) {
    companion object {
        val DEFAULT = DateAxisConfig(
            axisLineStyle = LineStyle(
                jsStyle = "black",
                width = 2.0
            ),
            markerRadius = 10.0,
            markerStyle = ClosedShapeStyle(
                jsStyle = "black",
                drawMode = DrawMode.FILL
            ),
            yearTextStyle = TextStyle(
                jsStyle = "black",
                font = "10px",
                drawMode = DrawMode.FILL,
                textAlign = CanvasTextAlign.CENTER
            )
        )
    }
}
