import timelinecommon.TimelineData

import kotlinx.browser.document
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.w3c.dom.CanvasRenderingContext2D as RenderContext
import org.w3c.dom.HTMLCanvasElement
import timelinejs.rendering.compound.renderable.Timeline
import timelinejs.datastructure.StaticPoint
import timelinejs.datastructure.StaticRectangle
import kotlin.math.pow

private const val ZOOM_EXPONENTIAL_BASE: Double = 0.9990473522592097

class TimelineHandler(timelineData: TimelineData) : InputListener {
    private val timeline: Timeline

    init {
        val canvas = document.getElementById("timelineCanvas") as HTMLCanvasElement
        val ctx = canvas.getContext("2d") as RenderContext

        timeline = Timeline(
            ctx,
            timelineData,
            bounds = StaticRectangle(0, 0, canvas.width, canvas.height)
        )

        InputHandler(this, canvas, document)
    }

    fun start() {
        console.log("Starting")
        timeline.render()
    }

    override fun onDragging(dist: StaticPoint) {
        timeline.translate(-dist.x)
    }

    override fun onScroll(scrollDist: StaticPoint, mousePos: StaticPoint) {
        val zoomMultiplier = ZOOM_EXPONENTIAL_BASE.pow(scrollDist.y)
        timeline.zoom(mousePos.x, zoomMultiplier)
    }
}

fun main() {
    MainScope().launch {
        try {
            val timelineData = getTimelineData()

            timelineHandler = TimelineHandler(timelineData)
            timelineHandler!!.start()
        } catch (e: Exception) {
            console.error(e.stackTraceToString())
        }
    }
}

// Expose the timelineHandler to javascript for debugging
var timelineHandler: TimelineHandler? = null
