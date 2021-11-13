package utilities

enum class Direction {
    HORIZONTAL,
    VERTICAL,
    DIAGONAL_NEG_SLOPE,
    DIAGONAL_POS_SLOPE
}

val directions = mapOf(
    Pair(Direction.VERTICAL, arrayOf(PositionDelta(0, 1), PositionDelta(0, -1))),
    Pair(Direction.HORIZONTAL, arrayOf(PositionDelta(1, 0), PositionDelta(-1, 0))),
    Pair(Direction.DIAGONAL_NEG_SLOPE, arrayOf(PositionDelta(1, -1), PositionDelta(-1, 1))),
    Pair(Direction.DIAGONAL_POS_SLOPE, arrayOf(PositionDelta(1, 1), PositionDelta(-1, -1)))
)
