package timelinejs

import kotlin.math.PI
import org.w3c.dom.CanvasRenderingContext2D as RenderContext

class Renderer(private val renderContext: RenderContext, private val bounds: Rectangle) {
    init {
        if (!bounds.topLeft.isZero()) {
            error("Render bounds must be at (0, 0)")
        }
    }

    var fillStyle: dynamic
        get() = renderContext.fillStyle
        set(value) {
            renderContext.fillStyle = value
        }

    fun fillCircle(center: Vector2D, radius: Double) {
        fill { circle(center, radius) }
    }

    fun fillRoundRect(rect: Rectangle, radius: Double) {
        fill { roundRect(rect, radius) }
    }

    fun line(x1: Double, y1: Double, x2: Double, y2: Double) {
        stroke {
            renderContext.moveTo(x1, y1)
            renderContext.lineTo(x2, y2)
        }
    }

    fun clear() {
        renderContext.clearRect(0.0, 0.0, bounds.width, bounds.height)
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

    private fun circle(center: Vector2D, radius: Double) {
        renderContext.arc(center.x, center.y, radius, 0.0, 2 * PI)
    }

    private fun roundRect(rect: Rectangle, radius: Double) {
        with(renderContext) {
            moveTo(rect.left + radius, rect.top)
            lineTo(rect.right - radius, rect.top)
            quadraticCurveTo(rect.right, rect.top, rect.right, rect.top + radius)
            lineTo(rect.right, rect.bottom - radius)
            quadraticCurveTo(rect.right, rect.bottom, rect.right - radius, rect.bottom)
            lineTo(rect.left + radius, rect.bottom)
            quadraticCurveTo(rect.left, rect.bottom, rect.left, rect.bottom - radius)
            lineTo(rect.left, rect.top + radius)
            quadraticCurveTo(rect.left, rect.top, rect.left + radius, rect.top)
        }
    }
}