package timelinejs.rendering

import timelinejs.datastructure.*
import timelinejs.rendering.compound.style.DrawMode
import kotlin.math.PI
import org.w3c.dom.CanvasRenderingContext2D as RenderContext

class Renderer(private val renderContext: RenderContext, private val bounds: StaticRectangle) {
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
    var lineCap by renderContext::lineCap
    var lineJoin by renderContext::lineJoin
    var miterLimit by renderContext::miterLimit
    var lineDash: Array<Double>
        get() = renderContext.getLineDash()
        set(value) = renderContext.setLineDash(value)

    fun fillText(text: String, location: Point) {
        val staticLocation = location.toStaticPoint()
        renderContext.fillText(text, staticLocation.x, staticLocation.y)
    }

    fun strokeText(text: String, location: Point) {
        val staticLocation = location.toStaticPoint()
        renderContext.strokeText(text, staticLocation.x, staticLocation.y)
    }

    fun line(point1: Point, point2: Point) {
        val staticPoint1 = point1.toStaticPoint()
        val staticPoint2 = point2.toStaticPoint()
        stroke {
            renderContext.moveTo(staticPoint1.x, staticPoint1.y)
            renderContext.lineTo(staticPoint2.x, staticPoint2.y)
        }
    }

    fun clear() {
        renderContext.clearRect(0.0, 0.0, bounds.width, bounds.height)
    }

    fun useDrawFun(drawMode: DrawMode, block: Renderer.() -> Unit) {
        when (drawMode) {
            DrawMode.FILL -> fill(block)
            DrawMode.STROKE -> stroke(block)
        }
    }

    private fun fill(block: Renderer.() -> Unit) {
        renderContext.beginPath()
        block()
        renderContext.fill()
    }

    private fun stroke(block: Renderer.() -> Unit) {
        renderContext.beginPath()
        block()
        renderContext.stroke()
    }

    fun circle(center: Point, radius: Double) {
        val staticCenter = center.toStaticPoint()
        renderContext.arc(staticCenter.x, staticCenter.y, radius, 0.0, 2 * PI)
    }

    fun roundRect(rect: Rectangle, radius: Double) {
        val staticRect = rect.toStaticRectangle()
        with(renderContext) {
            moveTo(staticRect.left + radius, staticRect.top)
            lineTo(staticRect.right - radius, staticRect.top)
            quadraticCurveTo(staticRect.right, staticRect.top, staticRect.right, staticRect.top + radius)
            lineTo(staticRect.right, staticRect.bottom - radius)
            quadraticCurveTo(staticRect.right, staticRect.bottom, staticRect.right - radius, staticRect.bottom)
            lineTo(staticRect.left + radius, staticRect.bottom)
            quadraticCurveTo(staticRect.left, staticRect.bottom, staticRect.left, staticRect.bottom - radius)
            lineTo(staticRect.left, staticRect.top + radius)
            quadraticCurveTo(staticRect.left, staticRect.top, staticRect.left + radius, staticRect.top)
        }
    }

    fun textSize(text: String): Size {
        val metrics = renderContext.measureText(text)
        val height = metrics.fontBoundingBoxAscent + metrics.fontBoundingBoxDescent
        return Size(metrics.width, height)
    }
}
