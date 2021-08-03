import org.w3c.dom.GlobalEventHandlers
import org.w3c.dom.css.CSSStyleDeclaration
import org.w3c.dom.events.MouseEvent
import timelinejs.Vector2D

class InputHandler(
    private val listener: InputListener,
    private val documentStyle: CSSStyleDeclaration,
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
        }
        with(documentHandler) {
            onmouseup = ::mouseUp
            onmousemove = ::mouseMove
        }
    }

    private fun mouseDown(mouseEvent: MouseEvent) {
        dragging = true
        dragStartPos = mouseEvent.getPos()
        documentStyle.cursor = "ew-resize"
    }

    private fun contextMenu(mouseEvent: MouseEvent) {
        mouseEvent.preventDefault()
        listener.onRightClick()
    }

    private fun mouseUp(mouseEvent: MouseEvent) {
        dragging = false
        listener.onDrag(mouseEvent.getPos() - dragStartPos)
        documentStyle.cursor = "auto"
    }

    private fun mouseMove(mouseEvent: MouseEvent) {
        val mousePos = mouseEvent.getPos()
        if (dragging) {
            listener.onDragging(mousePos - dragPrevPos)
        }
        dragPrevPos = mousePos
    }

    private fun MouseEvent.getPos() = Vector2D(x, y)
}

interface InputListener {
    fun onDragging(dist: Vector2D) {}
    fun onDrag(dist: Vector2D) {}
    fun onRightClick() {}
}
