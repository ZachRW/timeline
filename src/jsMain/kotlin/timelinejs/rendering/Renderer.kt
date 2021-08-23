package timelinejs.rendering

import timelinejs.rendering.datastructures.Point
import timelinejs.rendering.datastructures.Rectangle
import timelinejs.rendering.datastructures.Size
import kotlin.math.PI
import org.w3c.dom.CanvasRenderingContext2D as RenderContext

class Renderer(private val renderContext: RenderContext, private val bounds: Rectangle) {
    init {
        if (!bounds.location.isEmpty()) {
            error("Render bounds must be at (0, 0)")
        }
    }

    var fillStyle by renderContext::fillStyle
    var lineWidth by renderContext::lineWidth
    var strokeStyle by renderContext::strokeStyle
    var font by renderContext::font
    var textAlign by renderContext::textAlign
    var textBaseline by renderContext::textBaseline
    var lineDash: Array<Double>
        get() = renderContext.getLineDash()
        set(value) = renderContext.setLineDash(value)

    fun fillCircle(center: Point, radius: Double) {
        fill { circle(center, radius) }
    }

    fun fillText(text: String, x: Double, y: Double) {
        renderContext.fillText(text, x, y)
    }

    fun strokeText(text: String, x: Double, y: Double) {
        renderContext.strokeText(text, x, y)
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

    fun getDrawFun(drawMode: DrawMode): (Renderer.() -> Unit) -> Unit =
        when (drawMode) {
            DrawMode.FILL -> ::fill
            DrawMode.STROKE -> ::stroke
        }

    fun fill(block: Renderer.() -> Unit) {
        renderContext.beginPath()
        block()
        renderContext.fill()
    }

    fun stroke(block: Renderer.() -> Unit) {
        renderContext.beginPath()
        block()
        renderContext.stroke()
    }

    private fun circle(center: Point, radius: Double) {
        renderContext.arc(center.x, center.y, radius, 0.0, 2 * PI)
    }

    fun roundRect(rect: Rectangle, radius: Double) {
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

    fun textSize(text: String): Size {
        val metrics = renderContext.measureText(text)
        val height = metrics.fontBoundingBoxAscent + metrics.fontBoundingBoxDescent
        return Size(metrics.width, height)
    }
}
