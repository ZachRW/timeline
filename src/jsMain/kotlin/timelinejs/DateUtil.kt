package timelinejs

import timelinecommon.CommonDate
import kotlin.js.Date

fun CommonDate.toJsDate() = Date(year, month - 1, day)

operator fun Date.rangeTo(other: Date): DateRange =
    DateRange(this, other)

operator fun Date.compareTo(other: Date): Int =
    getTime().compareTo(other.getTime())

infix fun Date.plusMs(ms: Double): Date =
    Date(getTime() + ms)

data class DateRange(
    val start: Date,
    val end: Date
) {
    operator fun contains(value: Date) =
        value.getTime() in start.getTime()..end.getTime()
}

fun Date.Companion.fromYear(year: Int) =
    Date(year, 0, 1)
