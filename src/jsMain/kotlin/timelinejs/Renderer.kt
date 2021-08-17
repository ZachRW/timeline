package timelinejs

import kotlin.math.PI
import org.w3c.dom.CanvasRenderingContext2D as RenderContext

class Renderer(private val renderContext: RenderContext) {
    var fillStyle: dynamic
        get() = renderContext.fillStyle
        set(value) {
            renderContext.fillStyle = value
        }

    fun fillCircle(center: Vector2D, radius: Double) {
        fill { circle(center, radius) }
    }

    private fun circle(center: Vector2D, radius: Double) {
        renderContext.arc(center.x, center.y, radius, 0.0, 2 * PI)
    }

    private fun roundRect(pos: Vector2D, dim: Vector2D, radius: Double) {
        with(renderContext) {
            moveTo(pos.x + radius, pos.y)
            lineTo(pos.x + dim.x - radius, pos.y)
            TODO()
        }
    }

    private inline fun fill(block: () -> Unit) {
        renderContext.beginPath()
        block()
        renderContext.fill()
    }

    private inline fun stroke(block: () -> Unit) {
        renderContext.beginPath()
        block()
        renderContext.stroke()
    }
}