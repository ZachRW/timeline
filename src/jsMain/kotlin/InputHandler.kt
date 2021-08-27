import org.w3c.dom.GlobalEventHandlers
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.events.MouseEvent
import org.w3c.dom.events.WheelEvent
import timelinejs.datastructure.Point

class InputHandler(
    private val listener: InputListener,
    private val canvas: HTMLCanvasElement,
    documentHandler: GlobalEventHandlers
) {
    private var dragging = false
    private var dragStartPos = Point.EMPTY
    private var dragPrevPos = Point.EMPTY

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
        val scrollDist = Point(wheelEvent.deltaX, wheelEvent.deltaY)
        listener.onScroll(scrollDist, wheelEvent.mousePos())
    }

    private fun MouseEvent.mousePos(): Point {
        val canvasBounds = canvas.getBoundingClientRect()
        return Point(
            clientX - canvasBounds.left,
            clientY - canvasBounds.top
        )
    }
}

interface InputListener {
    fun onDragging(dist: Point) {}
    fun onDrag(dist: Point) {}
    fun onRightClick() {}
    fun onScroll(scrollDist: Point, mousePos: Point) {}
}
