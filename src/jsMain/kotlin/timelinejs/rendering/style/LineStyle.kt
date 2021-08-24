package timelinejs.rendering.style

import org.w3c.dom.CanvasLineCap

data class LineStyle(
    val jsStyle: dynamic,
    val width: Double,
    val cap: CanvasLineCap,
    val dash: List<Double>
)
