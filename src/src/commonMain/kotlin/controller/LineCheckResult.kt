package controller

import model.utilities.Position

data class LineCheckResult(
    val found: Boolean,
    val count: Int,
    val linePositions: Set<Position>? = null,

    // positions that are immediately after the line ends, in the same direction
    val positionsAfterLineEnds: Set<Position>? = null
)
