package timelinejs.rendering

import timelinejs.datastructure.StaticPoint
import kotlin.math.abs
import kotlin.math.sign

class Aligner(list1: List<Double>, list2: List<Double>) {
    // Only for javascript debugging
    constructor(list1: DoubleArray, list2: DoubleArray) : this(list1.toList(), list2.toList())

    private val differences = list1.zip(list2).map { (a, b) -> b - a }
    private val distinctDifferences = differences.sorted().distinct()

    fun getOffset(): Double {
        val averageDistanceFunction = getAverageDistanceFunction()

        val candidateOffsets = distinctDifferences.map { difference ->
            averageDistanceFunction.getIntersectionWithVFunctionX(difference)
        }

        val bestOffset = candidateOffsets.minByOrNull { candidate ->
            getDistances(candidate).meanDeviation()
        }

        return bestOffset ?: error("candidateOffsets is empty")
    }

    private fun getAverageDistanceFunction(): LinearPieceWiseFunction {
        val averageDistancePoints = distinctDifferences.map { difference ->
            StaticPoint(difference, getDistances(difference).average())
        }
        return LinearPieceWiseFunction(averageDistancePoints)
    }

    private fun getDistances(offset: Double): List<Double> {
        return differences.map { abs(it - offset) }
    }
}

private fun List<Double>.meanDeviation(): Double {
    val mean = average()
    return map { abs(it - mean) }.average()
}

private class LinearPieceWiseFunction(
    private val points: List<StaticPoint>,
    private val leftSlope: Double = -1.0,
    private val rightSlope: Double = 1.0
) {
    private val xIntercept: Double
        get() {
            var prevPoint = points[0]
            for (point in points.subList(1, points.size)) {
                if (point.y == 0.0) {
                    return point.x
                }
                if (prevPoint.y.sign != point.y.sign) {
                    val slope = (point.y - prevPoint.y) / (point.x - prevPoint.x)
                    return pointSlopeXIntercept(point, slope)
                }
                prevPoint = point
            }

            error("No x-intercept found")
        }

    fun getIntersectionWithVFunctionX(vFunctionXIntercept: Double): Double {
        return minusVFunction(vFunctionXIntercept).xIntercept
    }

    private fun minusVFunction(vFunctionXIntercept: Double): LinearPieceWiseFunction {
        val newPoints = points.map { point ->
            point.translate(0.0, -calcVFunction(vFunctionXIntercept, point.x))
        }

        return LinearPieceWiseFunction(
            newPoints,
            leftSlope + 1.0,
            rightSlope - 1.0
        )
    }
}

private fun calcVFunction(xIntercept: Double, x: Double): Double {
    return if (x >= xIntercept) {
        x - xIntercept
    } else {
        xIntercept - x
    }
}

private fun pointSlopeXIntercept(point: StaticPoint, slope: Double): Double {
    return point.x - point.y / slope
}
