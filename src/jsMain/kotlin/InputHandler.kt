import org.w3c.dom.GlobalEventHandlers
import org.w3c.dom.HTMLCanvasElement
import org.w3c.dom.events.MouseEvent
import org.w3c.dom.events.WheelEvent
import timelinejs.datastructure.StaticPoint

class InputHandler(
    private val listener: InputListener,
    private val canvas: HTMLCanvasElement,
    documentHandler: GlobalEventHandlers
) {
    private var dragging = false
    private var dragStartPos = StaticPoint.EMPTY
    private var dragPrevPos = StaticPoint.EMPTY

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
        val scrollDist = StaticPoint(wheelEvent.deltaX, wheelEvent.deltaY)
        listener.onScroll(scrollDist, wheelEvent.mousePos())
    }

    private fun MouseEvent.mousePos(): StaticPoint {
        val canvasBounds = canvas.getBoundingClientRect()
        return StaticPoint(
            clientX - canvasBounds.left,
            clientY - canvasBounds.top
        )
    }
}

interface InputListener {
    fun onDragging(dist: StaticPoint) {}
    fun onDrag(dist: StaticPoint) {}
    fun onRightClick() {}
    fun onScroll(scrollDist: StaticPoint, mousePos: StaticPoint) {}
}
