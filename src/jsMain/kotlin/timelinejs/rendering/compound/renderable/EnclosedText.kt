package timelinejs.rendering.compound.renderable

import timelinejs.rendering.compound.style.EnclosedTextStyle
import timelinejs.rendering.Renderer
import timelinejs.datastructure.Point
import timelinejs.datastructure.Rectangle
import timelinejs.rendering.compound.RenderParent
import timelinejs.rendering.simple.renderable.RoundRectangle
import timelinejs.rendering.simple.renderable.Text

class EnclosedText(
    location: Point,
    textStr: String,
    style: EnclosedTextStyle,
    renderer: Renderer
) : RenderParent() {
    private val roundRect: RoundRectangle
    private val text: Text
    val bounds: Rectangle

    init {
        val roundRectLocation = location.translate(style.textPadding, style.textPadding)
        val size = renderer.textSize(textStr)
        bounds = Rectangle(roundRectLocation, size)
        text = Text(location, textStr, style.textStyle, renderer)
        roundRect = RoundRectangle(bounds, style.roundRectStyle, renderer)

        children = mutableListOf(roundRect, text)
    }
}