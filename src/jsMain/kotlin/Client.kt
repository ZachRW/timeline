import timelinecommon.TimelineData

import kotlinx.browser.document
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.w3c.dom.CanvasRenderingContext2D as RenderContext
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.events.WheelEvent
import timelinejs.Timeline
import timelinejs.Vector2D
import kotlin.math.pow

private const val ZOOM_EXPONENTIAL_BASE: Double = 1.0009535561438964

class TimelineHandler(timelineData: TimelineData) : InputListener {
    private val timeline: Timeline

    init {
        val canvas = document.getElementById("timelineCanvas") as HTMLCanvasElement
        val ctx = canvas.getContext("2d") as RenderContext

        timeline = Timeline(
            ctx,
            timelineData,
            dim = Vector2D(canvas.width, canvas.height)
        )

        InputHandler(this, canvas, document)
    }

    fun start() {
        console.log("Starting")
        timeline.draw()
    }

    override fun onDragging(dist: Vector2D) {
        timeline.translate(-dist.x)
    }

    override fun onVerticalScroll(wheelEvent: WheelEvent) {
        val zoomMultiplier = ZOOM_EXPONENTIAL_BASE.pow(wheelEvent.deltaY)
        timeline.zoom(wheelEvent.x, zoomMultiplier)
    }
}

fun main() {
    MainScope().launch {
        val timelineData = getTimelineData()

        timelineHandler = TimelineHandler(timelineData)
        timelineHandler!!.start()
    }
}

// Expose the timelineHandler to javascript for debugging
var timelineHandler: TimelineHandler? = null
