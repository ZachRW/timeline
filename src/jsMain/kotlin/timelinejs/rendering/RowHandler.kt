package timelinejs.rendering

import timelinejs.rendering.compound.renderable.EventLabel
import kotlin.math.abs
import kotlin.math.sign

class RowHandler(private val eventLabels: List<EventLabel>) {
    private val mutableRows = mutableListOf<MutableList<EventLabel>>()

    val rows get(): List<MutableList<EventLabel>> = mutableRows

    operator fun get(row: Int): MutableList<EventLabel> {
        val index = rowToIndex(row)
        if (index !in mutableRows.indices)
            error("Row $row does not exist")

        return mutableRows[index]
    }

    fun reset() {
        mutableRows.clear()
        putEventLabelsInAlternatingRows()
    }

    fun moveEventLabelOutRow(eventLabel: EventLabel) {
        moveEventLabelToRow(eventLabel, eventLabel.row + eventLabel.row.sign)
    }

    private fun putEventLabelsInAlternatingRows() {
        for ((index, eventLabel) in eventLabels.withIndex()) {
            val row = if (index % 2 == 0) 1 else -1
            moveEventLabelToRow(eventLabel, row)
        }
    }

    private fun moveEventLabelToRow(eventLabel: EventLabel, row: Int) {
        val index = rowToIndex(row)
        val rowList = if (index in mutableRows.indices) {
            mutableRows[index]
        } else {
            val newRow = mutableListOf<EventLabel>()
            while (mutableRows.size < index) {
                mutableRows += mutableListOf<EventLabel>()
            }
            mutableRows += newRow
            newRow
        }

        eventLabel.row = row
        rowList += eventLabel
    }

    private fun rowToIndex(row: Int): Int {
        if (row == 0) error("Row must be non-zero")

        var index = (abs(row) - 1) * 2
        if (row < 0) index++
        return index
    }
}
