package timelinejs.rendering

import timelinejs.rendering.datastructures.Point
import timelinejs.rendering.datastructures.Rectangle

class EnclosedText(
    x: Double,
    y: Double,
    textStr: String,
    style: EnclosedTextStyle,
    renderer: Renderer
) : Renderable {
    private val roundRect: RoundRectangle
    private val text: Text

    init {
        val location = Point(x + style.textPadding, y + style.textPadding)
        val size = renderer.textSize(textStr)
        val bounds = Rectangle(location, size)
        text = Text(x, y, textStr, style.textStyle, renderer)
        roundRect = RoundRectangle(bounds, style.roundRectStyle, renderer)
    }

    override fun render() {
        roundRect.render()
        text.render()
    }
}