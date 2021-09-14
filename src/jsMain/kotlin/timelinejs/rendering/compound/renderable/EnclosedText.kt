package timelinejs.rendering.compound.renderable

import timelinejs.rendering.compound.style.EnclosedTextStyle
import timelinejs.rendering.Renderer
import timelinejs.datastructure.AbsolutePoint
import timelinejs.datastructure.Point
import timelinejs.datastructure.Rectangle
import timelinejs.datastructure.Size
import timelinejs.rendering.compound.RenderParent
import timelinejs.rendering.simple.renderable.RoundRectangle
import timelinejs.rendering.simple.renderable.Text

class EnclosedText(
    private val roundRect: RoundRectangle,
    text: Text
) : RenderParent() {
    val bounds by roundRect::bounds

    init {
        children = mutableListOf(roundRect, text)
    }

    companion object {
        fun create(
            location: Point,
            textStr: String,
            style: EnclosedTextStyle,
            renderer: Renderer
        ): EnclosedText {
            val paddingOffset = AbsolutePoint(style.textPadding, style.textPadding)

            val textLocation = location + paddingOffset
            val text = Text(textLocation, textStr, style.textStyle, renderer)

            val size = text.size + Size(paddingOffset) * 2.0
            val bounds = Rectangle(location, size)
            val roundRect = RoundRectangle(bounds, style.roundRectStyle, renderer)

            return EnclosedText(roundRect, text)
        }
    }
}
