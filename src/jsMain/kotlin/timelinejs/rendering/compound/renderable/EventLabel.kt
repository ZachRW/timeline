package timelinejs.rendering.compound.renderable

import timelinejs.rendering.Renderer
import timelinejs.datastructure.Point
import timelinejs.rendering.compound.RenderParent
import timelinejs.rendering.simple.renderable.Line
import timelinejs.rendering.compound.style.EventLabelStyle

class EventLabel(
    enclosedText: EnclosedText,
    stem: Line
) : RenderParent() {
    init {
        children = mutableListOf(stem, enclosedText)
    }

    companion object {
        fun create(
            location: Point,
            textStr: String,
            stemX: Double,
            stemBaseY: Double,
            style: EventLabelStyle,
            renderer: Renderer
        ): EventLabel {
            return EventLabelBuilder(
                location,
                textStr,
                stemX,
                stemBaseY,
                style,
                renderer
            ).build()
        }

        private class EventLabelBuilder(
            private val location: Point,
            private val textStr: String,
            private val stemX: Double,
            private val stemBaseY: Double,
            private val style: EventLabelStyle,
            private val renderer: Renderer
        ) {
            private lateinit var enclosedText: EnclosedText
            private lateinit var stem: Line

            fun build(): EventLabel {
                initEnclosedText()
                initStem()
                return EventLabel(enclosedText, stem)
            }

            private fun initEnclosedText() {
                EnclosedText(location, textStr, style.enclosedTextStyle, renderer)
            }

            private fun initStem() {
                val stemAttachY = if (stemBaseY < location.y) {
                    enclosedText.bounds.bottom
                } else {
                    enclosedText.bounds.top
                }

                stem = Line(
                    Point(stemX, stemAttachY),
                    Point(stemX, stemBaseY),
                    style.stemStyle,
                    renderer
                )
            }
        }
    }
}
