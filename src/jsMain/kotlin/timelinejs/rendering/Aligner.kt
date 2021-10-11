package timelinejs.rendering

import timelinejs.datastructure.StaticPoint
import kotlin.math.abs
import kotlin.math.sign

class Aligner(list1: List<Double>, list2: List<Double>) {
    private val differences = list1.zip(list2).map { (a, b) -> b - a }

    fun getOffset(): Double {
        val averageDistancePoints = differences.map { difference ->
            StaticPoint(difference, getDistances(difference).average())
        }
        val averageDistanceFunction =
            LinearPieceWiseFunction(averageDistancePoints)

        val candidateOffsets = differences.map { difference ->
            averageDistanceFunction.minusVFunction(difference).xIntercept
        }

        val bestOffset = candidateOffsets.minByOrNull { candidate ->
            getDistances(candidate).meanDeviation()
        }

        return bestOffset ?: error("candidateOffsets is empty")
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
    val xIntercept: Double
        get() {
            if (leftSlope == 0.0 && points[0].y == 0.0) {
                error("Left side zero")
            }
            if (rightSlope == 0.0 && points.last().y == 0.0) {
                error("Right side zero")
            }

            var prevPoint = points[0]
            for (point in points.subList(1, points.size)) {
                if (point.y == 0.0) {
                    return point.x
                }
                if (prevPoint.y.sign != point.y.sign) {
                    val slope = (point.y - prevPoint.y) - (point.x - prevPoint.x)
                    return pointSlopeXIntercept(point, slope)
                }
                prevPoint = point
            }

            error("No x-intercept found")
        }

    fun getIntersectionWithVFunctionX(vFunctionXIntercept: Double) {
        TODO("Replace xIntercept and minusVFunction with this function")
    }

    fun minusVFunction(xIntercept: Double): LinearPieceWiseFunction {
        val newPoints = points.map { point ->
            point.translate(0.0, -calcVFunction(xIntercept, point.x))
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
