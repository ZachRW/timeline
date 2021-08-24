package timelinejs.config

import timelinejs.rendering.style.TextStyle

data class DateAxisConfig(
    val lineWidth: Double,
    val lineStyle: String,
    val markerRadius: Double,
    val markerStyle: String,
    val yearTextConfig: TextStyle
) {
    companion object {
        val DEFAULT = DateAxisConfig(
            lineWidth = 2.0,
            lineStyle = "black",
            markerRadius = 10.0,
            markerStyle = "black",
            yearTextConfig = TextStyle(
                font = "10px",
                color = "black"
            )
        )
    }
}
