package timelinejs.rendering.style

data class RoundRectangleStyle(
    val jsStyle: dynamic,
    val drawMode: DrawMode,
    val radius: Double,
    val lineWidth: Double,
    val lineDash: List<Double>
)
