package timelinejs.rendering.compound.renderable

import timelinejs.View
import timelinejs.datastructure.*
import timelinejs.rendering.compound.style.EnclosedTextStyle
import timelinejs.rendering.Renderer
import timelinejs.rendering.compound.DynamicRenderParent
import timelinejs.rendering.simple.renderable.DynamicText
import timelinejs.rendering.simple.renderable.RoundRectangle
import timelinejs.rendering.simple.renderable.StaticText

class EnclosedText(
    location: DynamicPoint,
    textStr: String,
    style: EnclosedTextStyle,
    renderer: Renderer,
    view: View
) : DynamicRenderParent(view) {
    private val roundRect: RoundRectangle
    private val text: DynamicText
    val bounds get() = roundRect.bounds

    init {
        val paddingOffset = StaticPoint(style.textPadding, style.textPadding)

        val textLocation = location + paddingOffset
        text = DynamicText(textLocation, textStr, style.textStyle, renderer, view)

        val size = text.size + Size(paddingOffset) * 2.0
        val bounds = DynamicRectangle(location, size, view)
        roundRect = RoundRectangle(bounds, style.roundRectStyle, renderer, view)

        children = mutableListOf(roundRect, text)
    }
}
