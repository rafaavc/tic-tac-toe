package model.utilities.coordinates

abstract class CoordinateRange(
    protected val x1: Int,
    protected val x2: Int,
    protected val y1: Int,
    protected val y2: Int
) {
    abstract fun includesY(y: Int): CoordinateRangeCheckResult
    abstract fun includesX(x: Int): CoordinateRangeCheckResult
}