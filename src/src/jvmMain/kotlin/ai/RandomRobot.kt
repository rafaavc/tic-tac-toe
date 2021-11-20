package ai

import model.GameModel
import model.utilities.Position

class RandomRobot: Robot {
    override fun getNextPlay(gameModel: GameModel): Position {
        Thread.sleep(500)
        return gameModel.getPossibleMoves().random()
    }
}