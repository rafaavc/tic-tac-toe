package controller.states

import controller.GameState
import model.GameModel
import model.GameOverCheckResult
import react.StateSetter
import view.states.GameOver

class GameOverState(
    model: GameModel,
    setGameState: StateSetter<GameState?>,
    setWaitingForServer: StateSetter<Boolean>,
    val gameOverCheckResult: GameOverCheckResult
) : GameState(model, GameOver, setGameState, setWaitingForServer) {

    override fun isGameOver(): Boolean = true

}
