package timelinejs.rendering.compound.style

import timelinejs.SeriesColorPalette

data class TimelineStyle(
    val seriesColorPalette: SeriesColorPalette,
    val dateAxisStyle: DateAxisStyle
) {
    companion object {
        val DEFAULT = TimelineStyle(
            SeriesColorPalette.DEFAULT,
            DateAxisStyle.DEFAULT
        )
    }
}