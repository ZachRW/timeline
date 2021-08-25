package timelinejs.rendering.style

import org.w3c.dom.BUTT
import org.w3c.dom.CanvasLineCap

class LineStyle(
    val jsStyle: dynamic = "black",
    val width: Double = 1.0,
    val cap: CanvasLineCap = CanvasLineCap.BUTT,
    dash: Collection<Double> = listOf()
) {
    val dash: Array<Double> = dash.toTypedArray()
        get() = field.copyOf()
}
