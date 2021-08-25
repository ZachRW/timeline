package timelinejs.rendering.style

class RoundRectangleStyle(
    val radius: Double,
    jsStyle: dynamic,
    drawMode: DrawMode,
    lineWidth: Double,
    lineDash: Collection<Double>
) : ClosedShapeStyle(jsStyle, drawMode, lineWidth, lineDash)
