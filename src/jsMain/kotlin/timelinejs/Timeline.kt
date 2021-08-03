package timelinejs

import timelinecommon.TimelineData
import org.w3c.dom.CanvasRenderingContext2D
import kotlin.js.Date

class Timeline(
    private val ctx: CanvasRenderingContext2D,
    private val data: TimelineData,
    private var dim: Vector2D
) {
    var viewX: Date = data.startDate
        set(value) {
            field = value
            update()
        }
    var scale: Double = 1.0
        set(value) {
            field = value
            update()
        }

    fun draw() {
        ctx.clearRect(0.0, 0.0, dim.x, dim.y)
    }

    private fun update() {
        draw()
    }
}
