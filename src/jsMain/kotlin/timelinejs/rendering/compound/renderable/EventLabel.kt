package timelinejs.rendering.compound.renderable

import timelinejs.View
import timelinejs.datastructure.DynamicPoint
import timelinejs.rendering.DynamicRenderable
import timelinejs.rendering.Renderer
import timelinejs.rendering.compound.style.EventLabelStyle
import timelinejs.rendering.simple.renderable.DynamicLine
import kotlin.js.Date

open class EventLabel(
    private val textStr: String,
    val date: Date,
    private val stemBaseY: Double,
    private val dateAxisY: Double,
    private val style: EventLabelStyle,
    private val renderer: Renderer,
    view: View
) : DynamicRenderable(view) {
    private var enclosedText: EnclosedText =
        createEnclosedText(DynamicPoint(date, 0.0, view))
    private var stem: DynamicLine = createStem()

    open var row = 0
        set(value) {
            field = value

            val newY = if (row > 0) {
                dateAxisY - MIN_DIST_FROM_AXIS - bounds.height -
                        (bounds.height + PADDING) * (row - 1)
            } else {
                dateAxisY + MIN_DIST_FROM_AXIS +
                        (bounds.height + PADDING) * (-row - 1)
            }

            location = location.copy(y = newY)
        }

    val bounds get() = enclosedText.bounds
    var location: DynamicPoint
        get() = bounds.location
        set(value) {
            enclosedText = createEnclosedText(value)
            stem = createStem()
        }

    override fun render() {
        stem.render()
        enclosedText.render()
    }

    private fun createEnclosedText(location: DynamicPoint) =
        EnclosedText(
            location,
            textStr,
            style.enclosedTextStyle,
            renderer,
            view
        )

    private fun createStem(): DynamicLine {
        val stemAttachY = if (stemBaseY > enclosedText.bounds.y) {
            enclosedText.bounds.bottom
        } else {
            enclosedText.bounds.top
        }

        return DynamicLine(
            DynamicPoint(date, stemBaseY, view),
            DynamicPoint(date, stemAttachY, view),
            style.stemStyle,
            renderer,
            view
        )
    }

    companion object {
        const val MIN_DIST_FROM_AXIS = 100.0
        const val PADDING = 5.0
        const val STEM_MIN_DIST_FROM_EDGE = 5.0
    }
}
