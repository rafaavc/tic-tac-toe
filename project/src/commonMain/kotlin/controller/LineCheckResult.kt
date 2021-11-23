package controller

import model.utilities.Position

data class LineCheckResult(
    val found: Boolean,
    val count: Int,
    val linePositions: Set<Position>? = null,

    // positions that are immediately after the line ends, in the same direction
    // the boolean specifies whether there is a piece of the same type on the other side of the square
    val positionsAfterLineEnds: Set<Pair<Position, Boolean>>? = null
)
