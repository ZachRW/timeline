package timelinejs

import timelinecommon.CommonDate
import kotlin.js.Date

fun CommonDate.toJsDate() = Date(year, month, day)

operator fun Date.rangeTo(other: Date) = DateRange(this, other)

data class DateRange(
    val start: Date,
    val end: Date
) {
    operator fun contains(value: Date) =
        value.getTime() in start.getTime()..end.getTime()
}

fun Date.Companion.fromYear(year: Int) =
    Date(year, 1, 1)
