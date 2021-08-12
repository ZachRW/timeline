import org.w3c.dom.GlobalEventHandlers
import org.w3c.dom.css.CSSStyleDeclaration
import org.w3c.dom.events.MouseEvent
import org.w3c.dom.events.WheelEvent
import timelinejs.Vector2D

class InputHandler(
    private val listener: InputListener,
    canvasHandler: GlobalEventHandlers,
    documentHandler: GlobalEventHandlers
) {
    private var dragging = false
    private var dragStartPos = Vector2D()
    private var dragPrevPos = Vector2D()

    init {
        with(canvasHandler) {
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
        dragStartPos = mouseEvent.getPos()
    }

    private fun contextMenu(mouseEvent: MouseEvent) {
        mouseEvent.preventDefault()
        listener.onRightClick()
    }

    private fun mouseUp(mouseEvent: MouseEvent) {
        dragging = false
        listener.onDrag(mouseEvent.getPos() - dragStartPos)
    }

    private fun mouseMove(mouseEvent: MouseEvent) {
        val mousePos = mouseEvent.getPos()
        if (dragging) {
            listener.onDragging(mousePos - dragPrevPos)
        }
        dragPrevPos = mousePos
    }

    private fun wheel(wheelEvent: WheelEvent) {
        wheelEvent.preventDefault()
        listener.onVerticalScroll(wheelEvent)
    }

    private fun MouseEvent.getPos() = Vector2D(x, y)
}

interface InputListener {
    fun onDragging(dist: Vector2D) {}
    fun onDrag(dist: Vector2D) {}
    fun onRightClick() {}
    fun onVerticalScroll(wheelEvent: WheelEvent) {}
}
