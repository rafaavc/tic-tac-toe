package model.utilities.coordinates

class InnerCoordinateRange(
    x1: Int,
    x2: Int,
    y1: Int,
    y2: Int
) : CoordinateRange(x1, x2, y1, y2) {

    private fun includes(lower: Int, higher: Int, value: Int): CoordinateRangeCheckResult {
        if (value < lower) return CoordinateRangeCheckResult.FALSE
        if (value > higher) return CoordinateRangeCheckResult.FALSE_NO_GREATER
        return CoordinateRangeCheckResult.TRUE
    }

    override fun includesX(x: Int): CoordinateRangeCheckResult
        = includes(x1, x2, x)

    override fun includesY(y: Int): CoordinateRangeCheckResult
        = includes(y1, y2, y)

    companion object {
        fun all(boardSize: Int): CoordinateRange
            = InnerCoordinateRange(0, boardSize - 1, 0, boardSize - 1)
    }
}