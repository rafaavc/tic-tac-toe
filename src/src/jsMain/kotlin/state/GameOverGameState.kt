package state

import model.GameModel
import model.GameOverType
import model.GamePlayer
import model.utilities.Position

class GameOverGameState(gameModel: GameModel, private val gameOverType: GameOverType) : GameState(gameModel) {
    override fun canClickSquare(squarePosition: Position): Boolean = false
    override fun clickSquare(player: GamePlayer, squarePosition: Position): GameState? = null
    override fun isGameOver(): Boolean = true
    override fun getGameOverType(): GameOverType = gameOverType
}