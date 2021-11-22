package model

import model.utilities.*
import kotlinx.serialization.Serializable

@Serializable
class GameModel(
    val gameBoard: Array<Array<GamePiece>>,
    val player1GamePiece: GamePiece,
    private val boardSize: Int,
    private val target: Int
) {
    private val player2GamePiece = if (player1GamePiece == GamePiece.X) GamePiece.O else GamePiece.X
    var lastPlay: Position? = null
    var isEmpty = true

    constructor(player1GamePiece: GamePiece, boardSize: Int = 10, target: Int = 5)
        : this(Array(boardSize) {
            Array(boardSize) { GamePiece.EMPTY }
        }, player1GamePiece, boardSize, target)

    private fun copyGameBoard(): Array<Array<GamePiece>> {
        return Array(boardSize) { y ->
            Array(boardSize) { x ->
                gameBoard[y][x]
            }
        }
    }

    fun copy(): GameModel = GameModel(copyGameBoard(), player1GamePiece, boardSize, target)

    private fun isInsideBoard(position: Position) = position.x in 0 until boardSize && position.y in 0 until boardSize

    fun isValidPlay(position: Position) = isInsideBoard(position)
                                                    && gameBoard[position.y][position.x] == GamePiece.EMPTY

    fun getPossibleMoves(lowerScope: Boolean = false): MutableSet<Position> {
        // when lowerScope is true, this only gets the possible moves that do not
        // deviate more than 1 position from the sub-square where the current pieces are in the game
        var minY = boardSize - 1
        var maxY = 0
        var minX = minY
        var maxX = maxY

        var foundPiece = false
        if (lowerScope) {
            for ((y, line) in gameBoard.withIndex()) {
                for ((x, _) in line.withIndex()) {
                    if (gameBoard[y][x] != GamePiece.EMPTY) {
                        minY = minOf(minY, y)
                        maxY = maxOf(maxY, y)
                        minX = minOf(minX, x)
                        maxX = maxOf(maxX, x)
                        foundPiece = true
                    }
                }
            }
        }
        if (!lowerScope || !foundPiece) {
            minY = maxY.also { maxY = minY }
            minX = maxX.also { maxX = minX }
        }

        val possibleMoves = mutableSetOf<Position>()
        for ((y, line) in gameBoard.withIndex()) {
            if (y < minY - 1) continue
            if (y > maxY + 1) break
            for ((x, _) in line.withIndex()) {
                if (x < minX - 1) continue
                if (x > maxX + 1) break

                val position = Position(x, y)
                if (isValidPlay(position)) possibleMoves.add(position)
            }
        }
        return possibleMoves
    }

    private fun getPlayerPiece(player: GamePlayer): GamePiece
            = if (player == GamePlayer.PLAYER1) player1GamePiece else player2GamePiece

    fun makePlay(player: GamePlayer, position: Position, checkForValidPlay: Boolean = true): Boolean {
        if (checkForValidPlay && !isValidPlay(position)) return false
        val gamePiece = getPlayerPiece(player)

        gameBoard[position.y][position.x] = gamePiece
        lastPlay = position

        if (isEmpty) isEmpty = false

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

    fun checkGameOver(player: GamePlayer, position: Position, getWinningPieces: Boolean = false): GameOverCheckResult {
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
