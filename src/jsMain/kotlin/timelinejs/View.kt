package timelinejs

import kotlin.js.Date
import kotlin.properties.Delegates

private val MS_PER_PX_RANGE: ClosedFloatingPointRange<Double> = 6_000_000.0..35_000_000_000.0

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

    var startDateMs: Double
        get() = startDate.getTime()
        protected set(value) {
            startDate = Date(value)
        }
    val startDatePx: Double
        get() = dateToPx(startDate)

    var endDate: Date = calculateEndDate()
        private set
    val endDateMs: Double
        get() = endDate.getTime()
    val endDatePx: Double
        get() = dateToPx(endDate)

    private fun calculateEndDate() = Date(startDateMs + pxToMs(width))

    fun datePlusPx(date: Date, px: Double): Date =
        date plusMs pxToMs(px)

    /**
     * Returns milliseconds relative to [startDate] at [px]
     * @param [px] horizontal pixel position from the left
     */
    fun pxToMs(px: Double): Double = msPerPx * px

    /**
     * Returns the horizontal pixel position from the left of [ms]
     * @param [ms] milliseconds relative to [startDate]
     */
    fun msToPx(ms: Double): Double = ms / msPerPx

    fun dateToPx(date: Date): Double = msToPx(date.getTime() - startDateMs)

    fun pxToDate(px: Double): Date = Date(startDateMs + pxToMs(px))
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
        val actualMultiplier = coerceMultiplier(multiplier)

        val zoomMs = pxToMs(zoomPx)

        val startOffsetMs = zoomMs - (zoomMs / actualMultiplier)

        startDate = Date(startDateMs + startOffsetMs)
        msPerPx /= actualMultiplier
    }

    fun translate(deltaPx: Double) {
        startDateMs += pxToMs(deltaPx)
    }

    private fun coerceMultiplier(multiplier: Double): Double {
        val expectedMsPerPx = msPerPx / multiplier

        return if (expectedMsPerPx in MS_PER_PX_RANGE) {
            multiplier
        } else {
            val coercedMsPerPx = expectedMsPerPx.coerceIn(MS_PER_PX_RANGE)
            msPerPx / coercedMsPerPx
        }
    }
}
