package ai

import kotlinx.coroutines.delay
import model.GamePiece
import model.GameModel
import model.utilities.Position

class SequentialRobot: Robot {
    override fun getNextPlay(gameModel: GameModel): Position {
        for ((y, line) in gameModel.gameBoard.withIndex()) {
            for ((x, piece) in line.withIndex()) {
                if (piece == GamePiece.EMPTY) return Position(x, y)
            }
        }
        Thread.sleep(300)
        return Position(0, 0)
    }
}