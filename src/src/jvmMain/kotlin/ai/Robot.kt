package ai

import model.GameModel
import model.utilities.Position

interface Robot {
    fun getNextPlay(gameModel: GameModel): Position
}