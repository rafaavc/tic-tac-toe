package game

import utilities.*
import kotlinx.serialization.Serializable

@Serializable
class GameState(val gameBoard: Array<Array<GamePiece>>, private val humanGamePiece: GamePiece,
                private val boardSize: Int = 10, val gameOver: Boolean = false) {
    private val machineGamePiece = if (humanGamePiece == GamePiece.X) GamePiece.O else GamePiece.X

    constructor(humanGamePiece: GamePiece, boardSize: Int = 10)
        : this(Array(boardSize) {
            Array(boardSize) { GamePiece.EMPTY }
        }, humanGamePiece, boardSize)

    private fun isInsideBoard(position: Position) = position.x in 0 until boardSize && position.y in 0 until boardSize

    private fun isValidPlay(position: Position) = isInsideBoard(position)
                                                        && gameBoard[position.y][position.x] == GamePiece.EMPTY

    fun makePlay(player: GamePlayer, position: Position): GameState? {
        if (!isValidPlay(position)) return null
        val gamePiece = if (player == GamePlayer.HUMAN) humanGamePiece else machineGamePiece

        gameBoard[position.y][position.x] = gamePiece
        val isGameOver = checkGameOver(gamePiece, position)

        return GameState(gameBoard, humanGamePiece, boardSize, isGameOver)
    }

    private fun checkGameOver(gamePiece: GamePiece, position: Position, target: Int = 5): Boolean {
        // map that holds the count of consecutive $gamePiece pieces in each direction
        val counts = mutableMapOf<Direction, Int>()
        for (direction in directions.keys) counts[direction] = 1

        // the directions that are currently being considered
        var currentDirections = mutableMapOf<Direction, List<PositionDelta>>()
        for ((direction, deltas) in directions) currentDirections[direction] = deltas.toMutableList()

        // the directions that will be considered in next iteration (the "children" of the previous)
        var nextDirections = mutableMapOf<Direction, List<PositionDelta>>()


        // bfs-like algorithm to check if there is 5 ($target) $gamePiece pieces in a row in any direction
        var iteration = 1
        while(currentDirections.isNotEmpty() && iteration < target + 1) {
            for ((direction, positionDeltas) in currentDirections) {
                // for the current direction, the deltas that will be considered
                val nextDeltas = mutableListOf<PositionDelta>()

                for (positionDelta in positionDeltas) {
                    val currentPosition = position + positionDelta * iteration

                    if (isInsideBoard(currentPosition) && gameBoard[currentPosition.y][currentPosition.x] == gamePiece) {
                        nextDeltas.add(positionDelta)
                        continue
                    }

                    // if the current position does not continue the sequence,
                    // add the valid ones up until now to the direction count
                    counts[direction] = counts[direction]!! + iteration - 1
                    if (counts[direction]!! >= target) return true
                }

                if (nextDeltas.size > 0) nextDirections[direction] = nextDeltas
            }

            currentDirections = nextDirections.let {
                nextDirections = currentDirections
                nextDirections.clear()
                it
            }
            iteration++
        }

        return false
    }
}
