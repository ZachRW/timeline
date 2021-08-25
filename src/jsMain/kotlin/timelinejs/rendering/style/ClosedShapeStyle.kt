package timelinejs.rendering.style

open class ClosedShapeStyle(
    val jsStyle: dynamic = "black",
    val drawMode: DrawMode = DrawMode.FILL,
    val lineWidth: Double = 1.0,
    lineDash: Collection<Double> = listOf()
) {
    val lineDash: Array<Double> = lineDash.toTypedArray()
        get() = field.copyOf()
}
