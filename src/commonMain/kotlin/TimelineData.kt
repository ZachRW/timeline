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
    val namedDateRanges: List<NamedDateRange>,
    val events: List<Event>,
    val color: String?
)

@Serializable
data class NamedDateRange(
    val name: String,
    val start: CommonDate,
    val end: CommonDate
)

@Serializable
data class Event(
    val name: String,
    val date: CommonDate
)

@Serializable
data class CommonDate(
    val year: Int,
    val month: Int,
    val day: Int
)
