package timelinejs.rendering.compound.renderable

import timelinejs.View
import timelinejs.datastructure.DynamicPoint
import timelinejs.rendering.Renderer
import timelinejs.datastructure.StaticPoint
import timelinejs.datastructure.StaticRectangle
import timelinejs.rendering.DynamicRenderable
import timelinejs.rendering.Renderable
import timelinejs.rendering.compound.style.EventLabelStyle
import timelinejs.rendering.simple.renderable.DynamicLine
import kotlin.js.Date

class EventLabel(
    private val textStr: String,
    private val date: Date,
    private val stemBaseY: Double,
    private val style: EventLabelStyle,
    private val renderer: Renderer,
    initLocation: DynamicPoint,
    view: View
) : DynamicRenderable(view) {
    private var location: DynamicPoint = initLocation

    override fun render() {
        val enclosedText = createEnclosedText()
        val stem = createStem(enclosedText.bounds)

        stem.render()
        enclosedText.render()
    }

    private fun createEnclosedText() =
        EnclosedText(
            location,
            textStr,
            style.enclosedTextStyle,
            renderer
        )

    private fun createStem(enclosedTextBounds: StaticRectangle): DynamicLine {
        val stemAttachY = if (stemBaseY < enclosedTextBounds.y) {
            enclosedTextBounds.bottom
        } else {
            enclosedTextBounds.top
        }

        return DynamicLine(
            DynamicPoint(date, stemBaseY, view),
            DynamicPoint(date, stemAttachY, view),
            style.stemStyle,
            renderer,
            view
        )
    }
}
