package controller

import model.GameModel
import model.GamePiece
import model.utilities.Direction
import model.utilities.Position
import model.utilities.PositionDelta
import model.utilities.directions

class LineChecker(private val model: GameModel) {
    private val gameBoard = model.gameBoard

    fun checkLineAtPosition(
        position: Position,
        target: Int,
        getLinePieces: Boolean = false,
        getEmptyPositionsAfterLineEnds: Boolean = false,
        directionToCheck: Direction = Direction.ALL
    ) : LineCheckResult {

        // checks if there is a line that includes $position that matches the target
        // if there isn't, returns the biggest line that includes that position
        // return: (foundLineWithDesiredLength, piecesThatMakeTheLine, emptyPositionsAfterLineEnds)

        val gamePiece = gameBoard[position.y][position.x]
        if (gamePiece == GamePiece.EMPTY) error("Checking line at position $position, but that position has no piece")

        val directionsToCheck = if (directionToCheck == Direction.ALL) directions
                                else mapOf(Pair(directionToCheck, directions[directionToCheck]!!))

        val linePieces = (if (getLinePieces) mutableMapOf<Direction, MutableSet<Position>>() else null)?.also {
            for ((direction, _) in directionsToCheck) it[direction] = mutableSetOf(position)
        }

        // this is a list of pairs, where each pair has the position of the empty square
        // and of the square that comes after it (by following the line)
        val emptyPositionsAfterLineEnds
            = (if (getEmptyPositionsAfterLineEnds) mutableMapOf<Direction, MutableSet<Triple<Direction, Position, Position?>>>() else null)?.also {
                for ((direction, _) in directionsToCheck) it[direction] = mutableSetOf()
            }

        // map that holds the count of consecutive $gamePiece pieces in each direction
        val counts = mutableMapOf<Direction, Int>()
        for (direction in directionsToCheck.keys) counts[direction] = 1

        // the directions that are currently being considered
        var currentDirections = mutableMapOf<Direction, List<PositionDelta>>()
        for ((direction, deltas) in directionsToCheck) currentDirections[direction] = deltas.toMutableList()

        // the directions that will be considered in next iteration (the "children" of the previous)
        var nextDirections = mutableMapOf<Direction, List<PositionDelta>>()


        // bfs-like algorithm to check if there is $target $gamePiece pieces in a row in any direction
        var iteration = 1
        while(currentDirections.isNotEmpty() && iteration < target + 1) {
            for ((direction, positionDeltas) in currentDirections) {
                // for the current direction, the deltas that will be considered
                val nextDeltas = mutableListOf<PositionDelta>()

                for (positionDelta in positionDeltas) {
                    val currentPosition = position + positionDelta * iteration

                    val currentTotalCountForDirection = counts[direction]!! + iteration - 1

                    if (model.isInsideBoard(currentPosition)
                        && gameBoard[currentPosition.y][currentPosition.x] == gamePiece
                        && currentTotalCountForDirection < target) {

                        nextDeltas.add(positionDelta)
                        linePieces?.run {
                            this[direction]!!.add(currentPosition)
                        }
                        continue
                    }

                    if (model.isInsideBoard(currentPosition)
                            && gameBoard[currentPosition.y][currentPosition.x] == GamePiece.EMPTY) {
                        emptyPositionsAfterLineEnds?.run {
                            val nextPosition = currentPosition + positionDelta
                            this[direction]!!.add(
                                Triple(direction, currentPosition, if (model.isInsideBoard(nextPosition)) nextPosition else null))
                        }
                    }

                    // if the current position does not continue the sequence,
                    // add the valid ones up until now to the direction count
                    counts[direction] = currentTotalCountForDirection
                    if (counts[direction]!! >= target)
                        return LineCheckResult(
                            true,
                            target,
                            linePieces?.run {
                                linePieces[direction]!!
                            },
                            emptyPositionsAfterLineEnds?.run {
                                emptyPositionsAfterLineEnds[direction]!!
                            }
                        )
                }

                if (nextDeltas.size > 0) nextDirections[direction] = nextDeltas
            }

            currentDirections = nextDirections.also {
                nextDirections = currentDirections
                nextDirections.clear()
            }
            iteration++
        }

        // getting the direction that contains the largest line
        val maxDirection = counts.maxWithOrNull { e1, e2 -> e1.value.compareTo(e2.value) }!!

        return LineCheckResult(false,
            maxDirection.value,
            linePieces?.run {
                linePieces[maxDirection.key]!!
            },
            emptyPositionsAfterLineEnds?.run {
                if (maxDirection.value == 1) { // if the line has only one piece, we need to return the surrounding empty positions
                    val emptyPositions = mutableSetOf<Triple<Direction, Position, Position?>>()
                    for ((_, tripleSet) in this) emptyPositions += tripleSet
                    emptyPositions
                }
                else emptyPositionsAfterLineEnds[maxDirection.key]!!
            }
        )
    }
}