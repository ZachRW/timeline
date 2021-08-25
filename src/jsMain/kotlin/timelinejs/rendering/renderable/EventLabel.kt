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
        fun create(
            x: Double,
            y: Double,
            textStr: String,
            stemX: Double,
            stemBaseY: Double,
            style: EventLabelStyle,
            renderer: Renderer
        ): EventLabel {
            return EventLabelBuilder(
                x,
                y,
                textStr,
                stemX,
                stemBaseY,
                style,
                renderer
            ).build()
        }

        private class EventLabelBuilder(
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

            fun build(): EventLabel {
                initEnclosedText()
                initStem()
                return EventLabel(enclosedText, stem)
            }

            private fun initEnclosedText() {
                EnclosedText(x, y, textStr, style.enclosedTextStyle, renderer)
            }

            private fun initStem() {
                val stemAttachY = if (stemBaseY < y) {
                    enclosedText.bounds.bottom
                } else {
                    enclosedText.bounds.top
                }

                stem = Line(
                    x1 = stemX, y1 = stemAttachY,
                    x2 = stemX, y2 = stemBaseY,
                    style = style.stemStyle,
                    renderer = renderer
                )
            }
        }
    }
}
