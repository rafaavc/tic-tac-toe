package controller

import model.*
import model.utilities.Position
import model.utilities.coordinates.CoordinateRange
import model.utilities.coordinates.CoordinateRangeCheckResult
import model.utilities.coordinates.InnerCoordinateRange

class GameController(private val model: GameModel) {
    private val gameBoard = model.gameBoard
    private val lineChecker = LineChecker(model)

    fun getPossibleMoves(lowerScope: Boolean = false): MutableSet<Position> {
        // when lowerScope is true, this only gets the possible moves that do not
        // deviate more than 1 position from the sub-square where the current pieces are in the game
        val movesRange = if (lowerScope) getBoardScopeWithPieces(1)
                            else InnerCoordinateRange.all(model.boardSize)

        val possibleMoves = mutableSetOf<Position>()
        iterateThroughBoardRange(movesRange) { x, y ->
            val position = Position(x, y)
            if (model.isValidPlay(position)) possibleMoves.add(position)
        }
        return possibleMoves
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

    fun checkGameOver(player: GamePlayer, position: Position, getWinningPieces: Boolean): GameOverCheckResult {
        val (isOver, winningPieces) = lineChecker.checkLineAtPosition(position, model.target, getWinningPieces)

        if (isOver) return GameOverCheckResult(GameOverCheckResult.getGameOverType(player), winningPieces)
        return checkFilledBoard()
    }

    private fun getBoardScopeWithPieces(padding: Int = 0): CoordinateRange {
        // returns a smaller scope of coordinates in the board that contain pieces, with a $padding of empty spaces

        var minY = model.boardSize - 1
        var maxY = 0
        var minX = minY
        var maxX = maxY

        var foundPiece = false
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
        if (!foundPiece) {
            minY = maxY.also { maxY = minY }
            minX = maxX.also { maxX = minX }
        }

        return InnerCoordinateRange(minX - padding, maxX + padding, minY - padding, maxY + padding)
    }

    private fun iterateThroughBoardRange(range: CoordinateRange, action: (x: Int, y: Int) -> Unit) {
        for ((y, line) in gameBoard.withIndex()) {
            val yRangeCheckResult = range.includesY(y)
            if (yRangeCheckResult == CoordinateRangeCheckResult.FALSE_NO_GREATER) break
            if (yRangeCheckResult == CoordinateRangeCheckResult.FALSE) continue

            for ((x, _) in line.withIndex()) {
                val xRangeCheckResult = range.includesX(x)
                if (xRangeCheckResult == CoordinateRangeCheckResult.FALSE_NO_GREATER) break
                if (xRangeCheckResult == CoordinateRangeCheckResult.FALSE) continue

                action(x, y)
            }
        }
    }
}
