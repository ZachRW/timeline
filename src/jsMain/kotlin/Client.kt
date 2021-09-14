import timelinecommon.TimelineData

import kotlinx.browser.document
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.w3c.dom.CanvasRenderingContext2D as RenderContext
import org.w3c.dom.HTMLCanvasElement
import timelinejs.rendering.compound.renderable.Timeline
import timelinejs.datastructure.AbsolutePoint
import timelinejs.datastructure.Rectangle
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
            bounds = Rectangle.createAbsolute(0, 0, canvas.width, canvas.height)
        )

        InputHandler(this, canvas, document)
    }

    fun start() {
        console.log("Starting")
        timeline.render()
    }

    override fun onDragging(dist: AbsolutePoint) {
        timeline.translate(-dist.x)
    }

    override fun onScroll(scrollDist: AbsolutePoint, mousePos: AbsolutePoint) {
        val zoomMultiplier = ZOOM_EXPONENTIAL_BASE.pow(scrollDist.y)
        timeline.zoom(mousePos.x, zoomMultiplier)
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
