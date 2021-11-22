package ai

import controller.GameController
import model.GameModel
import model.utilities.Position

class RandomRobot: Robot {
    override fun getNextPlay(model: GameModel): Position {
        Thread.sleep(500)
        return GameController(model).getPossibleMoves().random()
    }
}