package timelinejs.rendering.compound.renderable

import timelinejs.View
import timelinejs.datastructure.DynamicPoint
import timelinejs.rendering.DynamicRenderable
import timelinejs.rendering.Renderer
import timelinejs.rendering.compound.style.EventLabelStyle
import timelinejs.rendering.simple.renderable.DynamicLine
import kotlin.js.Date

// debug variable
var debugEventLabels = arrayOf("Doctor Strange in the Multiverse of Madness", "Black Panther: Wakanda Forever")

open class EventLabel(
    val textStr: String,
    val date: Date,
    private val dateAxisY: Double,
    private val style: EventLabelStyle,
    private val renderer: Renderer,
    view: View
) : DynamicRenderable(view) {
    private var enclosedText: EnclosedText =
        createEnclosedText(DynamicPoint(date, 0.0, view))
    private var stem: DynamicLine = createStem()

    open var rowNum = 0
        set(value) {
            field = value
            if (rowNum == 0) return

            val newY = if (rowNum > 0) {
                dateAxisY - MIN_DIST_FROM_AXIS - bounds.height -
                        (bounds.height + PADDING) * (rowNum - 1)
            } else {
                dateAxisY + MIN_DIST_FROM_AXIS +
                        (bounds.height + PADDING) * (-rowNum - 1)
            }

            location = location.copy(y = newY)
        }

    val bounds get() = enclosedText.bounds
    var location: DynamicPoint
        get() = bounds.location
        set(value) {
            // debug code
            if (textStr in debugEventLabels) {
                fun formatDouble(x: Double) =
                    x.toInt().toString().padStart(4)

                val startPx = formatDouble(view.dateToPx(location.xDate))
                val endPx = formatDouble(view.dateToPx(value.xDate))
                console.log("${"'$textStr'".padStart(45)}: $startPx -> $endPx")
                console.log() // dummy statement for breakpoint
            }

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
        val stemAttachY = if (dateAxisY > enclosedText.bounds.y) {
            enclosedText.bounds.bottom
        } else {
            enclosedText.bounds.top
        }

        return DynamicLine(
            DynamicPoint(date, dateAxisY, view),
            DynamicPoint(date, stemAttachY, view),
            style.stemStyle,
            renderer,
            view
        )
    }

    override fun toString(): String {
        return textStr
    }

    companion object {
        const val MIN_DIST_FROM_AXIS = 100.0
        const val PADDING = 5.0
        const val STEM_MIN_DIST_FROM_EDGE = 15.0
    }
}
