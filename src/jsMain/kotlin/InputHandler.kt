import org.w3c.dom.GlobalEventHandlers
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.events.MouseEvent
import org.w3c.dom.events.WheelEvent
import timelinejs.Vector2D

class InputHandler(
    private val listener: InputListener,
    private val canvas: HTMLCanvasElement,
    documentHandler: GlobalEventHandlers
) {
    private var dragging = false
    private var dragStartPos = Vector2D()
    private var dragPrevPos = Vector2D()

    init {
        with(canvas) {
            onmousedown = ::mouseDown
            oncontextmenu = { mouseEvent ->
                contextMenu(mouseEvent)
                false
            }
            onwheel = ::wheel
        }
        with(documentHandler) {
            onmouseup = ::mouseUp
            onmousemove = ::mouseMove
        }
    }

    private fun mouseDown(mouseEvent: MouseEvent) {
        dragging = true
        dragStartPos = mouseEvent.mousePos()
    }

    private fun contextMenu(mouseEvent: MouseEvent) {
        mouseEvent.preventDefault()
        listener.onRightClick()
    }

    private fun mouseUp(mouseEvent: MouseEvent) {
        dragging = false
        listener.onDrag(mouseEvent.mousePos() - dragStartPos)
    }

    private fun mouseMove(mouseEvent: MouseEvent) {
        val mousePos = mouseEvent.mousePos()
        if (dragging) {
            listener.onDragging(mousePos - dragPrevPos)
        }
        dragPrevPos = mousePos
    }

    private fun wheel(wheelEvent: WheelEvent) {
        wheelEvent.preventDefault()
        val scrollDist = Vector2D(wheelEvent.deltaX, wheelEvent.deltaY)
        listener.onScroll(scrollDist, wheelEvent.mousePos())
    }

    private fun MouseEvent.mousePos(): Vector2D {
        val canvasBounds = canvas.getBoundingClientRect()
        return Vector2D(
            clientX - canvasBounds.left,
            clientY - canvasBounds.top
        )
    }
}

interface InputListener {
    fun onDragging(dist: Vector2D) {}
    fun onDrag(dist: Vector2D) {}
    fun onRightClick() {}
    fun onScroll(scrollDist: Vector2D, mousePos: Vector2D) {}
}
