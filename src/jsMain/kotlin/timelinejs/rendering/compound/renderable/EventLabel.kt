package timelinejs.rendering.compound.renderable

import timelinejs.rendering.Renderer
import timelinejs.datastructure.Point
import timelinejs.datastructure.Rectangle
import timelinejs.rendering.Renderable
import timelinejs.rendering.compound.style.EventLabelStyle
import timelinejs.rendering.simple.renderable.Line

class EventLabel(
    private val textStr: String,
    private val stemBaseY: Double,
    private val style: EventLabelStyle,
    private val renderer: Renderer,
    initLocation: Point = Point.EMPTY,
    initStemX: Double = 0.0
) : Renderable {
    private var location: Point = initLocation
    private var stemX: Double = initStemX

    override fun render() {
        val enclosedText = createEnclosedText()
        val stem = createStem(enclosedText.bounds)

        stem.render()
        enclosedText.render()
    }

    private fun createEnclosedText() =
        EnclosedText.create(
            location,
            textStr,
            style.enclosedTextStyle,
            renderer
        )

    private fun createStem(enclosedTextBounds: Rectangle): Line {
        val stemAttachY = if (stemBaseY < enclosedTextBounds.y) {
            enclosedTextBounds.bottom
        } else {
            enclosedTextBounds.top
        }

        return Line(
            Point(stemX, stemBaseY),
            Point(stemX, stemAttachY),
            style.stemStyle,
            renderer
        )
    }
}

/*
class EventLabelBuilder {
    private var stemX: Double? = null
    private var stemBaseY: Double? = null
    private var style: EventLabelStyle? = null
    private var series: JsSeries? = null
    private var renderer: Renderer? = null

    private val enclosedTextBuilder = EnclosedTextBuilder()

    fun setLocation(location: Point) {
        enclosedTextBuilder.setLocation(location)
    }

    fun setText(text: String) {
        enclosedTextBuilder.setText(text)
    }

    fun setStemX(stemX: Double) {
        this.stemX = stemX
    }

    fun setStemBaseY(stemBaseY: Double) {
        this.stemBaseY = stemBaseY
    }

    fun setStyle(style: EventLabelStyle) {
        this.style = style
        enclosedTextBuilder.setStyle(style.enclosedTextStyle)
    }

    fun setSeries(series: JsSeries) {
        this.series = series
    }

    fun setRenderer(renderer: Renderer) {
        this.renderer = renderer
    }

    fun build(): EventLabel {
        val series = checkNotNull(series)
        val enclosedText = enclosedTextBuilder.build()
        val stem = buildStem()
        return EventLabel(enclosedText, stem, series)
    }

    private fun buildStem(): Line {
        val stemX = checkNotNull(stemX)
        val stemBaseY = checkNotNull(stemBaseY)
        val style = checkNotNull(style)
        val renderer = checkNotNull(renderer)

        val stemAttachY = if (stemBaseY < enclosedTextBuilder.bounds.y) {
            enclosedTextBuilder.bounds.bottom
        } else {
            enclosedTextBuilder.bounds.top
        }

        return Line(
            Point(stemX, stemAttachY),
            Point(stemX, stemBaseY),
            style.stemStyle,
            renderer
        )
    }
}
*/
