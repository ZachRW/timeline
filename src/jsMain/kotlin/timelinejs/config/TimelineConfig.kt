package timelinejs.config

import timelinejs.SeriesColorPalette

data class TimelineConfig(
    val seriesColorPalette: SeriesColorPalette,
    val dateAxisConfig: DateAxisConfig
) {
    companion object {
        val DEFAULT = TimelineConfig(
            SeriesColorPalette.DEFAULT,
            DateAxisConfig.DEFAULT
        )
    }
}