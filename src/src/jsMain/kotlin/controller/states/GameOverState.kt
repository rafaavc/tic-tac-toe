package controller.states

import controller.GameState
import model.GameModel
import model.GameOverCheckResult
import model.GameOverType
import model.utilities.Position
import react.StateSetter
import view.states.GameOver

class GameOverState(
    model: GameModel,
    setGameState: StateSetter<GameState?>,
    setWaitingForServer: StateSetter<Boolean>,
    private val gameOverCheckResult: GameOverCheckResult
) : GameState(model, GameOver, setGameState, setWaitingForServer) {

    override fun isGameOver(): Boolean = true
    override fun getGameOverType(): GameOverType = gameOverCheckResult.type
    override fun getWinningPieces(): Set<Position>? = gameOverCheckResult.winningPieces
}
