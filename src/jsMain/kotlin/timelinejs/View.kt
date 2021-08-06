package timelinejs

import kotlin.js.Date

open class View protected constructor(
    startDate: Date,
    msPerPx: Double,
    val width: Double
) {
    open var startDate: Date = startDate
        protected set(value) {
            field = value
            endDate = calculateEndDate()
            startDateMs = calculateStartDateMs()
        }
    open var msPerPx: Double = msPerPx
        protected set(value) {
            field = value
            endDate = calculateEndDate()
            endDateMs = calculateEndDateMs()
        }

    var endDate: Date = calculateEndDate()
        private set
    var startDateMs: Int = calculateStartDateMs()
        private set
    var endDateMs: Int = calculateEndDateMs()
        private set

    private fun calculateEndDate() = startDate + (msPerPx * width).toInt()
    private fun calculateStartDateMs() = startDate.getDate()
    private fun calculateEndDateMs() = endDate.getDate()

    fun pxToMs(px: Double): Int = (msPerPx * px).toInt()
    fun msToPx(ms: Int): Double = ms / msPerPx
    fun dateToPx(date: Date): Double = msToPx(date.getDate())

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
        set(value) {
            super.startDate = value
        }
    override var msPerPx: Double
        get() = super.msPerPx
        set(value) {
            super.msPerPx = value
        }

    fun zoom(zoomPx: Double, multiplier: Double) {
        val zoomMs = pxToMs(zoomPx)

        val startMsZoomed = zoomMs - multiplier * (zoomMs - startDateMs)

        startDate = Date(startMsZoomed)
        msPerPx *= multiplier
    }

    fun translate(deltaPx: Double) {
        startDate += pxToMs(deltaPx)
    }
}
