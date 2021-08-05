package timelinejs

import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.TextMetrics
import timelinecommon.CommonDate
import timelinecommon.TimelineData
import kotlin.js.Date

data class Vector2D(val x: Double, val y: Double) {
    constructor(x: Number, y: Number) : this(x.toDouble(), y.toDouble())
    constructor() : this(0.0, 0.0)

    operator fun plus(other: Vector2D) =
        Vector2D(x + other.x, y + other.y)

    operator fun minus(other: Vector2D) =
        Vector2D(x - other.x, y - other.y)

    operator fun unaryMinus() =
        Vector2D(-x, -y)

    operator fun times(other: Vector2D) =
        Vector2D(x * other.x, y * other.y)

    operator fun div(other: Vector2D) =
        Vector2D(x / other.x, y / other.y)

    operator fun times(scalar: Double) =
        Vector2D(x * scalar, y * scalar)

    operator fun div(scalar: Double) =
        Vector2D(x / scalar, y / scalar)
}

val TextMetrics.height: Double
    get() = fontBoundingBoxAscent + fontBoundingBoxDescent

fun CommonDate.toDate() = Date(year, month, day)

operator fun Date.plus(ms: Double) = Date(getTime() + ms)

operator fun Date.rangeTo(other: Date) = DateRange(this, other)

val TimelineData.dateRange: DateRange
    get() {
        val dates: List<Date> = seriesList.flatMap { series ->
            series.events.map { it.date.toDate() } +
                    series.namedDateRanges.map { it.start.toDate() }
        }

        if (dates.isEmpty()) {
            error("No dates found")
        }

        // Not-null assertions should never fail because dates is not empty
        val start = dates.minByOrNull { it.getTime() }!!
        val end = dates.maxByOrNull { it.getTime() }!!

        return start..end
    }

data class DateRange(
    val start: Date,
    val end: Date
) {
    operator fun contains(value: Date) = value.getTime() in start.getTime()..end.getTime()
}

fun Date.Companion.fromYear(year: Int) = Date(year, 1, 1)

fun yearDatesWithin(dateRange: DateRange): List<Date> {
    var startYear = dateRange.start.getFullYear()
    val endYear = dateRange.end.getFullYear()

    if (Date.fromYear(startYear) !in dateRange) {
        startYear++
    }

    return (startYear..endYear).map(Date::fromYear)
}

inline fun CanvasRenderingContext2D.fill(block: CanvasRenderingContext2D.() -> Unit) {
    beginPath()
    block()
    fill()
}

inline fun CanvasRenderingContext2D.stroke(block: CanvasRenderingContext2D.() -> Unit) {
    beginPath()
    block()
    stroke()
}
