package timelinejs.rendering.simple.style

import timelinejs.rendering.compound.style.DrawMode

open class ClosedShapeStyle(
    val jsStyle: dynamic = "black",
    val drawMode: DrawMode = DrawMode.FILL,
    val lineWidth: Double = 1.0,
    lineDash: Collection<Double> = listOf()
) {
    val lineDash: Array<Double> = lineDash.toTypedArray()
        get() = field.copyOf()
}
