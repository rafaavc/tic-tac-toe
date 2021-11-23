package controller

import model.utilities.Direction
import model.utilities.Position

data class LineCheckResult(
    val found: Boolean,
    val count: Int,
    val linePositions: Set<Position>? = null,

    // empty positions that are immediately after the line ends, in the same direction
    // the second element of the pair is the position of the empty square and the third is the next position (by following the line)
    val emptyPositionsAfterLineEnds: Set<Triple<Direction, Position, Position?>>? = null
)
