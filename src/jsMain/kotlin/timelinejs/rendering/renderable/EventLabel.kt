package timelinejs.rendering.renderable

import timelinejs.rendering.Renderer
import timelinejs.rendering.style.EventLabelStyle

class EventLabel(
    private val enclosedText: EnclosedText,
    private val stem: Line
) : Renderable {

    override fun render() {
        stem.render()
        enclosedText.render()
    }

    companion object {
    }
}

class EventLabelBuilder(
    private val x: Double,
    private val y: Double,
    private val textStr: String,
    private val stemX: Double,
    private val stemBaseY: Double,
    private val style: EventLabelStyle,
    private val renderer: Renderer
) {
    private lateinit var enclosedText: EnclosedText
    private lateinit var stem: Line

    fun create(): EventLabel {
        initEnclosedText()
        initStem()
        return EventLabel(enclosedText, stem)
    }

    private fun initEnclosedText() {
        EnclosedText(x, y, textStr, style.enclosedTextStyle, renderer)
    }

    private fun initStem() {
        if (stemBaseY < y) {
            initDescendingStem()
        } else {
            initAscendingStem()
        }
    }

    private fun initDescendingStem() {
    }

    private fun initAscendingStem() {
    }
}
