package timelinejs.rendering.compound.renderable

import timelinejs.rendering.Renderer
import timelinejs.datastructure.AbsolutePoint
import timelinejs.datastructure.AbsoluteRectangle
import timelinejs.rendering.Renderable
import timelinejs.rendering.compound.style.EventLabelStyle
import timelinejs.rendering.simple.renderable.Line

class AbsoluteEventLabel(
    private val textStr: String,
    private val stemBaseY: Double,
    private val style: EventLabelStyle,
    private val renderer: Renderer,
    initLocation: AbsolutePoint = AbsolutePoint.EMPTY,
    initStemX: Double = 0.0
) : Renderable {
    private var location: AbsolutePoint = initLocation
    private var stemX: Double = initStemX

    override fun render() {
        val enclosedText = createEnclosedText()
        val stem = createStem(enclosedText.bounds)

        stem.render()
        enclosedText.render()
    }

    private fun createEnclosedText() =
        EnclosedText.create(
            location,
            textStr,
            style.enclosedTextStyle,
            renderer
        )

    private fun createStem(enclosedTextBounds: AbsoluteRectangle): Line {
        val stemAttachY = if (stemBaseY < enclosedTextBounds.y) {
            enclosedTextBounds.bottom
        } else {
            enclosedTextBounds.top
        }

        return Line(
            AbsolutePoint(stemX, stemBaseY),
            AbsolutePoint(stemX, stemAttachY),
            style.stemStyle,
            renderer
        )
    }
}
