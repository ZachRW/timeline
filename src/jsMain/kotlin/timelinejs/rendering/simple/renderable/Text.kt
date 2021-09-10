package timelinejs.rendering.simple.renderable

import timelinejs.rendering.Renderer
import timelinejs.datastructure.AbsolutePoint
import timelinejs.datastructure.AbsoluteRectangle
import timelinejs.datastructure.Size
import timelinejs.rendering.Renderable
import timelinejs.rendering.simple.style.TextStyle
import timelinejs.rendering.compound.style.DrawMode

class Text(
    private val location: AbsolutePoint,
    private val text: String,
    private val style: TextStyle,
    private val renderer: Renderer
) : Renderable {
    val size: Size

    init {
        style.applyStyle(renderer)
        size = renderer.textSize(text)
    }

    override fun render() {
        style.applyStyle(renderer)
        fillOrStroke()
    }

    private fun fillOrStroke() {
        when (style.drawMode) {
            DrawMode.FILL ->
                renderer.fillText(text, location)
            DrawMode.STROKE ->
                renderer.strokeText(text, location)
        }
    }
}

class TextBuilder {
    private var location: AbsolutePoint? = null
    private var text: String? = null
    private var style: TextStyle? = null
    private var renderer: Renderer? = null

    val size: Size
        get() {
            val text = checkNotNull(text)
            val style = checkNotNull(style)
            val renderer = checkNotNull(renderer)

            style.applyStyle(renderer)
            return renderer.textSize(text)
        }
    val bounds: AbsoluteRectangle
        get() {
            val location = checkNotNull(location)
            return AbsoluteRectangle(location, size)
        }

    fun setLocation(location: AbsolutePoint) {
        this.location = location
    }

    fun setText(text: String) {
        this.text = text
    }

    fun setStyle(style: TextStyle) {
        this.style = style
    }

    fun setRenderer(renderer: Renderer) {
        this.renderer = renderer
    }

    fun build(): Text {
        val location = checkNotNull(location)
        val text = checkNotNull(text)
        val style = checkNotNull(style)
        val renderer = checkNotNull(renderer)

        return Text(location, text, style, renderer)
    }
}
