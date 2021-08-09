package timelinejs

import kotlin.js.Date
import kotlin.properties.Delegates

open class View protected constructor(
    startDate: Date,
    msPerPx: Double,
    val width: Double
) {
    open var startDate: Date by Delegates.observable(startDate)
    { _, _, _ -> endDate = calculateEndDate() }
        protected set

    open var msPerPx: Double by Delegates.observable(msPerPx)
    { _, _, _ -> endDate = calculateEndDate() }
        protected set

    var endDate: Date = calculateEndDate()
        private set
    var startDateMs: Double
        get() = startDate.getTime()
        protected set(value) {
            startDate = Date(value)
        }
    val endDateMs: Double
        get() = endDate.getTime()

    private fun calculateEndDate() = Date(startDateMs + pxToMs(width))

    fun pxToMs(px: Double): Double = msPerPx * px
    fun msToPx(ms: Double): Double = ms / msPerPx
    fun dateToPx(date: Date): Double = msToPx(date.getTime() - startDateMs)

    fun yearDatesWithin(): List<Date> {
        var startYear = startDate.getFullYear()
        val endYear = endDate.getFullYear()

        if (Date.fromYear(startYear) !in startDate..endDate) {
            startYear++
        }

        return (startYear..endYear).map(Date::fromYear)
    }
}

class MutableView(
    startDate: Date,
    msPerPx: Double,
    width: Double
) : View(startDate, msPerPx, width) {
    constructor(startDate: Date, endDate: Date, width: Double) : this(
        startDate,
        (endDate.getTime() - startDate.getTime()) / width,
        width
    )

    override var startDate: Date
        get() = super.startDate
        set(value) { super.startDate = value }

    override var msPerPx: Double
        get() = super.msPerPx
        set(value) { super.msPerPx = value }

    fun zoom(zoomPx: Double, multiplier: Double) {
        val zoomMs = pxToMs(zoomPx)

        val startMsZoomed = zoomMs - multiplier * (zoomMs - startDateMs)

        startDate = Date(startMsZoomed)
        msPerPx *= multiplier
    }

    fun translate(deltaPx: Double) {
        startDateMs += pxToMs(deltaPx)
    }
}
