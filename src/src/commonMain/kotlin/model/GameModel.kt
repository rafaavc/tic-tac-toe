package model

import model.utilities.*
import kotlinx.serialization.Serializable

@Serializable
class GameModel(val gameBoard: Array<Array<GamePiece>>, val humanGamePiece: GamePiece,
                private val boardSize: Int = 10) {

    private val machineGamePiece = if (humanGamePiece == GamePiece.X) GamePiece.O else GamePiece.X
    var lastPlay: Position? = null

    constructor(humanGamePiece: GamePiece, boardSize: Int = 10)
        : this(Array(boardSize) {
            Array(boardSize) { GamePiece.EMPTY }
        }, humanGamePiece, boardSize)

    private fun copyGameBoard(): Array<Array<GamePiece>> {
        return Array(boardSize) { y ->
            Array(boardSize) { x ->
                gameBoard[y][x]
            }
        }
    }

    fun copy(): GameModel = GameModel(copyGameBoard(), humanGamePiece)

    private fun isInsideBoard(position: Position) = position.x in 0 until boardSize && position.y in 0 until boardSize

    fun isValidPlay(position: Position) = isInsideBoard(position)
                                                    && gameBoard[position.y][position.x] == GamePiece.EMPTY

    fun getPossibleMoves(): MutableSet<Position> {
        val possibleMoves = mutableSetOf<Position>()
        for ((y, line) in gameBoard.withIndex()) {
            for ((x, _) in line.withIndex()) {
                val position = Position(x, y)
                if (isValidPlay(position)) possibleMoves.add(position)
            }
        }
        return possibleMoves
    }

    private fun getPlayerPiece(player: GamePlayer): GamePiece
            = if (player == GamePlayer.HUMAN) humanGamePiece else machineGamePiece

    fun makePlay(player: GamePlayer, position: Position, checkForValidPlay: Boolean = true): Boolean {
        if (checkForValidPlay && !isValidPlay(position)) return false
        val gamePiece = getPlayerPiece(player)

        gameBoard[position.y][position.x] = gamePiece
        lastPlay = position

        return true
    }

    private fun checkFilledBoard(): GameOverCheckResult {
        for (line in gameBoard) {
            for (piece in line) {
                if (piece == GamePiece.EMPTY) return GameOverCheckResult(GameOverType.NOT_OVER)
            }
        }
        // only gets here if there is no empty spot on the board
        return GameOverCheckResult(GameOverType.DRAW)
    }

    fun checkGameOver(player: GamePlayer, position: Position, getWinningPieces: Boolean = false, target: Int = 5): GameOverCheckResult {
        val gamePiece = getPlayerPiece(player)

        val winningPieces = (if (getWinningPieces) mutableMapOf<Direction, MutableSet<Position>>() else null)?.also {
            for ((direction, _) in directions) it[direction] = mutableSetOf(position)
        }

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

                    val currentTotalCountForDirection = counts[direction]!! + iteration - 1

                    if (isInsideBoard(currentPosition) && gameBoard[currentPosition.y][currentPosition.x] == gamePiece && currentTotalCountForDirection < target) {
                        nextDeltas.add(positionDelta)
                        winningPieces?.run {
                            this[direction]!!.add(currentPosition)
                        }
                        continue
                    }

                    // if the current position does not continue the sequence,
                    // add the valid ones up until now to the direction count
                    counts[direction] = currentTotalCountForDirection
                    if (counts[direction]!! >= target)
                        return GameOverCheckResult(GameOverCheckResult.getGameOverType(player),
                                    winningPieces?.run {
                                        winningPieces[direction]!!
                                    })
                }

                if (nextDeltas.size > 0) nextDirections[direction] = nextDeltas
            }

            currentDirections = nextDirections.also {
                nextDirections = currentDirections
                nextDirections.clear()
            }
            iteration++
        }

        return checkFilledBoard()
    }
}
