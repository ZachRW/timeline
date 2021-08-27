package timelinejs.rendering.compound.style

import timelinejs.rendering.simple.style.ClosedShapeStyle

class RoundRectangleStyle(
    val radius: Double,
    jsStyle: dynamic,
    drawMode: DrawMode,
    lineWidth: Double,
    lineDash: Collection<Double>
) : ClosedShapeStyle(jsStyle, drawMode, lineWidth, lineDash)
