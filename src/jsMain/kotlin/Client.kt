import timelinecommon.TimelineData

import kotlinx.browser.document
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import timelinejs.Timeline
import timelinejs.Vector2D

private class TimelineHandler(timelineData: TimelineData) : InputListener {
    private val timeline: Timeline
    private val canvas: HTMLCanvasElement

    init {
        console.log("Initializing")

        canvas = document.getElementById("timelineCanvas") as HTMLCanvasElement
        val ctx = canvas.getContext("2d") as CanvasRenderingContext2D

        timeline = Timeline(
            ctx,
            timelineData,
            dim = Vector2D(canvas.width, canvas.height)
        )

        InputHandler(this, document.body!!.style, canvas, document)
    }

    fun start() {
        console.log("Starting")
        timeline.draw()

        console.log(timeline)
    }

    override fun onDragging(dist: Vector2D) {
        timeline.translate(-dist.x)
    }
}

fun main() {
    MainScope().launch {
        console.log("Launching MainScope coroutine")
        val timelineData = getTimelineData()
        console.log(timelineData)

        TimelineHandler(timelineData).start()
    }
}
