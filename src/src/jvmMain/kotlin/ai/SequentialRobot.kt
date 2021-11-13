package ai

import game.GamePiece
import game.GameState
import utilities.Position

class SequentialRobot: Robot {
    override fun getNextPlay(gameState: GameState): Position {
        for ((y, line) in gameState.gameBoard.withIndex()) {
            for ((x, piece) in line.withIndex()) {
                if (piece == GamePiece.EMPTY) return Position(x, y)
            }
        }
        return Position(0, 0)
    }
}