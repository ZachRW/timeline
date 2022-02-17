package timelinejs.rendering

import timelinejs.rendering.compound.renderable.EventLabel
import kotlin.math.abs
import kotlin.math.sign
import collectionsjs.*

class RowHandler(private val eventLabels: List<EventLabel>) {
    private val mutableRows = mutableListOf<Row>()

    val rows get(): List<Row> = mutableRows

    operator fun get(rowNum: Int): Row {
        val index = rowNumToIndex(rowNum)
        if (index !in mutableRows.indices)
            error("Row $rowNum does not exist")

        return mutableRows[index]
    }

    fun reset() {
        mutableRows.clear()
        putEventLabelsInAlternatingRows()
    }

    fun moveEventLabelOutRow(eventLabel: EventLabel) {
        moveEventLabelToRow(eventLabel, eventLabel.rowNum + eventLabel.rowNum.sign)
    }

    private fun putEventLabelsInAlternatingRows() {
        for ((index, eventLabel) in eventLabels.withIndex()) {
            val rowNum = if (index % 2 == 0) 1 else -1
            moveEventLabelToRow(eventLabel, rowNum)
        }
    }

    private fun moveEventLabelToRow(eventLabel: EventLabel, rowNum: Int) {
        getOrAddRow(rowNum) += eventLabel
        eventLabel.rowNum = rowNum
    }

    private fun getOrAddRow(rowNum: Int): Row {
        val index = rowNumToIndex(rowNum)
        return if (index in mutableRows.indices) {
            mutableRows[index]
        } else {
            addRow(rowNum, index)
        }
    }

    private fun addRow(rowNum: Int, index: Int): Row {
        val newRow = Row(rowNum)
        for (fillerIndex in mutableRows.size until index) {
            mutableRows += Row(indexToRowNum(fillerIndex))
        }
        mutableRows += newRow
        return newRow
    }

    private fun rowNumToIndex(rowNum: Int): Int {
        if (rowNum == 0) error("rowNum must be non-zero")

        var index = (abs(rowNum) - 1) * 2
        if (rowNum < 0) index++
        return index
    }

    private fun indexToRowNum(index: Int): Int {
        val sign = if (index % 2 == 0) 1 else -1
        return (index / 2 + 1) * sign
    }
}
