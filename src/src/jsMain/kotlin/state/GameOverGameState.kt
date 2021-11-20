package state

import model.GameModel
import model.GameOverCheckResult
import model.GameOverType
import model.GamePlayer
import model.utilities.Position
import react.StateSetter
import view.components.GameBoard
import view.components.GameOver

class GameOverGameState(gameModel: GameModel, setGameState: StateSetter<GameState?>, private val gameOverCheckResult: GameOverCheckResult) : GameState(gameModel, GameOver, setGameState) {
    override fun isGameOver(): Boolean = true
    override fun getGameOverType(): GameOverType = gameOverCheckResult.type
    override fun getWinningPieces(): Set<Position>? = gameOverCheckResult.winningPieces
}
