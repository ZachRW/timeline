package timelinejs.rendering.compound.style

import timelinejs.rendering.simple.style.ClosedShapeStyle

class RoundRectangleStyle(
    val radius: Double,
    jsStyle: dynamic,
    drawMode: DrawMode,
    lineWidth: Double = 0.0,
    lineDash: Collection<Double> = listOf()
) : ClosedShapeStyle(jsStyle, drawMode, lineWidth, lineDash)
