package timelinejs.rendering.compound.style

import timelinejs.rendering.simple.style.LineStyle

data class DateAxisStyle(
    val axisLineStyle: LineStyle,
    val markerStyle: DateMarkerStyle
) {
    companion object {
        val DEFAULT = DateAxisStyle(
            axisLineStyle = LineStyle(
                jsStyle = "black",
                width = 2.0
            ),
            markerStyle = DateMarkerStyle.DEFAULT
        )
    }
}
