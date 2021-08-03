package timelinecommon

import kotlinx.serialization.Serializable

@Serializable
data class TimelineData(
    val seriesList: List<Series>
) {
    companion object {
        const val path = "/data/mcu.json"
    }
}

@Serializable
data class Series(
    val timeRanges: List<TimeRange>,
    val events: List<Event>
)

@Serializable
data class TimeRange(
    val name: String,
    val start: Date,
    val end: Date
)

@Serializable
data class Event(
    val name: String,
    val date: Date
)

@Serializable
data class Date(
    val year: Int,
    val month: Int,
    val day: Int
)
