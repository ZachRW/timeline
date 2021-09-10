package timelinejs.rendering.compound.renderable

import timelinejs.rendering.compound.style.EnclosedTextStyle
import timelinejs.rendering.Renderer
import timelinejs.datastructure.AbsolutePoint
import timelinejs.datastructure.AbsoluteRectangle
import timelinejs.datastructure.Size
import timelinejs.rendering.compound.RenderParent
import timelinejs.rendering.simple.renderable.RoundRectangle
import timelinejs.rendering.simple.renderable.Text
import timelinejs.rendering.simple.renderable.TextBuilder

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
            location: AbsolutePoint,
            textStr: String,
            style: EnclosedTextStyle,
            renderer: Renderer
        ): EnclosedText {
            val paddingOffset = AbsolutePoint(style.textPadding, style.textPadding)

            val textLocation = location + paddingOffset
            val text = Text(textLocation, textStr, style.textStyle, renderer)

            val size = text.size + Size(paddingOffset) * 2.0
            val bounds = AbsoluteRectangle(location, size)
            val roundRect = RoundRectangle(bounds, style.roundRectStyle, renderer)

            return EnclosedText(roundRect, text)
        }
    }
}

class EnclosedTextBuilder {
    private var location: AbsolutePoint? = null
    private var style: EnclosedTextStyle? = null
    private var renderer: Renderer? = null

    private val textBuilder = TextBuilder()

    val size: Size
        get() {
            val style = checkNotNull(style)
            return textBuilder.size + Size(style.textPadding, style.textPadding) * 2.0
        }
    val bounds: AbsoluteRectangle
        get() {
            val location = checkNotNull(location)
            return AbsoluteRectangle(location, size)
        }

    fun setLocation(location: AbsolutePoint) {
        val style = checkNotNull(style)
        this.location = location

        val textLocation = location.translate(style.textPadding, style.textPadding)
        textBuilder.setLocation(textLocation)
    }

    fun setText(text: String) {
        textBuilder.setText(text)
    }

    fun setStyle(style: EnclosedTextStyle) {
        this.style = style
        textBuilder.setStyle(style.textStyle)
    }

    fun setRenderer(renderer: Renderer) {
        this.renderer = renderer
        textBuilder.setRenderer(renderer)
    }

    fun build(): EnclosedText {
        val style = checkNotNull(style)
        val renderer = checkNotNull(renderer)

        val roundRect = RoundRectangle(bounds, style.roundRectStyle, renderer)
        val text = textBuilder.build()

        return EnclosedText(roundRect, text)
    }
}
