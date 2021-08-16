package timelinejs.config

data class DateAxisConfig(
    val lineWidth: Double,
    val lineStyle: String,
    val markerRadius: Double,
    val markerStyle: String,
    val yearTextConfig: TextConfig
) {
    companion object {
        val DEFAULT = DateAxisConfig(
            lineWidth = 2.0,
            lineStyle = "black",
            markerRadius = 10.0,
            markerStyle = "black",
            yearTextConfig = TextConfig(
                font = "10px",
                color = "black"
            )
        )
    }
}
