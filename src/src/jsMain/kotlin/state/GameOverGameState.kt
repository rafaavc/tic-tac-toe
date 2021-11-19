package state

import model.GameModel
import model.GameOverType
import model.GamePlayer
import model.utilities.Position
import react.StateSetter
import view.components.GameBoard
import view.components.GameOver

class GameOverGameState(gameModel: GameModel, setGameState: StateSetter<GameState?>, private val gameOverType: GameOverType) : GameState(gameModel, GameOver, setGameState) {
    override fun isGameOver(): Boolean = true
    override fun getGameOverType(): GameOverType = gameOverType
}
