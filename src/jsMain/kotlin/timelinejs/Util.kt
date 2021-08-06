package timelinejs

import org.w3c.dom.CanvasRenderingContext2D as CanvasContext
import org.w3c.dom.TextMetrics
import timelinecommon.TimelineData
import kotlin.js.Date
import kotlin.math.PI

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

val TimelineData.dateRange: DateRange
    get() {
        val dates: List<Date> = seriesList.flatMap { series ->
            series.events.map { it.date.toDate() } +
                    series.namedDateRanges.map { it.start.toDate() } +
                    series.namedDateRanges.map { it.end.toDate() }
        }

        if (dates.isEmpty()) {
            error("No dates found")
        }

        // Not-null assertions should never fail because dates is not empty
        val start = dates.minByOrNull { it.getTime() }!!
        val end = dates.maxByOrNull { it.getTime() }!!

        return start..end
    }

inline fun CanvasContext.fill(block: CanvasContext.() -> Unit) {
    beginPath()
    block()
    fill()
}

inline fun CanvasContext.stroke(block: CanvasContext.() -> Unit) {
    beginPath()
    block()
    stroke()
}

inline fun CanvasContext.circle(center: Vector2D, radius: Double) {
    arc(center.x, center.y, radius, 0.0, 2 * PI)
}

inline fun CanvasContext.fillCircle(center: Vector2D, radius: Double) =
    fill { circle(center, radius) }
