package state

import model.GameModel
import model.GameOverType
import model.GamePlayer
import model.utilities.Position

abstract class GameState(val gameModel: GameModel) {
    abstract fun canClickSquare(squarePosition: Position): Boolean
    abstract fun clickSquare(player: GamePlayer, squarePosition: Position): GameState?
    abstract fun isGameOver(): Boolean
    abstract fun getGameOverType(): GameOverType
}