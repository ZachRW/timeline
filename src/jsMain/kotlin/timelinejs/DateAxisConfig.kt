package timelinejs

import org.w3c.dom.CanvasRenderingContext2D as RenderContext

data class DateAxisConfig(
    val lineWidth: Double,
    val lineStyle: String,
    val markerRadius: Double,
    val markerStyle: String
) {
    companion object {
        val DEFAULT = DateAxisConfig(
            lineWidth = 2.0,
            lineStyle = "black",
            markerRadius = 10.0,
            markerStyle = "black"
        )
    }
}

fun RenderContext.applyLineConfig(dateAxisConfig: DateAxisConfig) {
    lineWidth = dateAxisConfig.lineWidth
    strokeStyle = dateAxisConfig.lineStyle
}

fun RenderContext.applyMarkerConfig(dateAxisConfig: DateAxisConfig) {
    fillStyle = dateAxisConfig.markerStyle
}
