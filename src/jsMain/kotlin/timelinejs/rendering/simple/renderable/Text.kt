package timelinejs.rendering.simple.renderable

import timelinejs.rendering.Renderer
import timelinejs.datastructure.Point
import timelinejs.datastructure.Rectangle
import timelinejs.datastructure.Size
import timelinejs.rendering.Renderable
import timelinejs.rendering.simple.style.TextStyle
import timelinejs.rendering.compound.style.DrawMode

class Text(
    private val location: Point,
    private val text: String,
    private val style: TextStyle,
    private val renderer: Renderer
) : Renderable {
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
    private var location: Point? = null
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
    val bounds: Rectangle
        get() {
            val location = checkNotNull(location)
            return Rectangle(location, size)
        }

    fun setLocation(location: Point) {
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
