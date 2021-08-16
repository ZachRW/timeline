package timelinejs

import timelinecommon.Event
import timelinecommon.NamedDateRange
import timelinecommon.Series
import timelinecommon.TimelineData
import kotlin.js.Date

class JsTimelineData(val seriesList: List<JsSeries>) {
    val dateRange: DateRange by lazy {
        if (dates.isEmpty()) {
            error("No dates found")
        }

        // Not-null assertions should never fail because dates is not empty
        val start = dates.minByOrNull { it.getTime() }!!
        val end = dates.maxByOrNull { it.getTime() }!!

        start..end
    }

    val dates: List<Date> by lazy {
        val dates = mutableListOf<Date>()
        for (series in seriesList) {
            for ((_, date) in series.events) {
                dates += date
            }
            for ((_, start, end) in series.namedDateRanges) {
                dates += start
                dates += end
            }
        }
        dates
    }
}

class JsSeries(
    val namedDateRanges: List<JsNamedDateRange>,
    val events: List<JsEvent>,
    val color: String
) {
    var visible = true
}

data class JsNamedDateRange(
    val name: String,
    val start: Date,
    val end: Date
)

data class JsEvent(
    val name: String,
    val date: Date
)

fun TimelineData.toJsTimelineData(seriesColorPalette: SeriesColorPalette): JsTimelineData {
    val seriesColorProvider = SeriesColorProvider(seriesColorPalette)
    val jsSeriesList = seriesList.map { it.toJsSeries(seriesColorProvider) }
    return JsTimelineData(jsSeriesList)
}

private fun Series.toJsSeries(seriesColorProvider: SeriesColorProvider): JsSeries {
    val jsNamedDateRanges = namedDateRanges.map { it.toJsNamedDateRange() }
    val jsEvents = events.map { it.toJsEvent() }
    val jsColor = color ?: seriesColorProvider.next()
    return JsSeries(jsNamedDateRanges, jsEvents, jsColor)
}

private fun NamedDateRange.toJsNamedDateRange() =
    JsNamedDateRange(name, start.toJsDate(), end.toJsDate())

private fun Event.toJsEvent() =
    JsEvent(name, date.toJsDate())
