package timelinejs.rendering.compound.renderable

import timelinejs.JsSeries
import timelinejs.rendering.Renderer
import timelinejs.datastructure.Point
import timelinejs.rendering.compound.RenderParent
import timelinejs.rendering.simple.renderable.Line
import timelinejs.rendering.compound.style.EventLabelStyle

class EventLabel internal constructor(
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
}

class EventLabelBuilder {
//    private var location: Point? = null
//    private var textStr: String? = null
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
        TODO()
    }

//    fun build(): EventLabel {
//        initEnclosedText()
//        initStem()
//        return EventLabel(enclosedText, stem, series)
//    }
//
//    private fun initEnclosedText() {
//        EnclosedText(location, textStr, style.enclosedTextStyle, renderer)
//    }
//
//    private fun initStem() {
//        val stemAttachY = if (stemBaseY < location.y) {
//            enclosedText.bounds.bottom
//        } else {
//            enclosedText.bounds.top
//        }
//
//        stem = Line(
//            Point(stemX, stemAttachY),
//            Point(stemX, stemBaseY),
//            style.stemStyle,
//            renderer
//        )
//    }
}
