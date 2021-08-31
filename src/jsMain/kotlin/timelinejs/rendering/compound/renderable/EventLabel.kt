package timelinejs.rendering.compound.renderable

import timelinejs.JsSeries
import timelinejs.rendering.Renderer
import timelinejs.datastructure.Point
import timelinejs.rendering.compound.RenderParent
import timelinejs.rendering.simple.renderable.Line
import timelinejs.rendering.compound.style.EventLabelStyle

class EventLabel(
    enclosedText: EnclosedText,
    stem: Line,
    private val series: JsSeries
) : RenderParent() {
    init {
        children = mutableListOf(stem, enclosedText)
    }

    override fun render() {
        if (series.visible) {
            renderChildren()
        }
    }

    companion object {
        fun create(
            location: Point,
            textStr: String,
            stemX: Double,
            stemBaseY: Double,
            style: EventLabelStyle,
            series: JsSeries,
            renderer: Renderer
        ): EventLabel {
            return EventLabelBuilder(
                location,
                textStr,
                stemX,
                stemBaseY,
                style,
                series,
                renderer
            ).build()
        }

        private class EventLabelBuilder(
            private val location: Point,
            private val textStr: String,
            private val stemX: Double,
            private val stemBaseY: Double,
            private val style: EventLabelStyle,
            private val series: JsSeries,
            private val renderer: Renderer
        ) {
            private lateinit var enclosedText: EnclosedText
            private lateinit var stem: Line

            fun build(): EventLabel {
                initEnclosedText()
                initStem()
                return EventLabel(enclosedText, stem, series)
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
