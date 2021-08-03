package timelinejs

import org.w3c.dom.TextMetrics
import timelinecommon.Date
import timelinecommon.TimelineData
import kotlin.js.Date as JSDate

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

fun Date.toJSDate() = JSDate(year, month, day)

val TimelineData.startDate: JSDate
    get() {
        val dates: List<JSDate> = seriesList.flatMap { series ->
            series.events.map { it.date.toJSDate() } +
                    series.timeRanges.map { it.start.toJSDate() }
        }

        TODO()
//        return dates.minByOrNull {  }
    }
