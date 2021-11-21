package controller.states

import controller.GameState
import model.GameModel
import model.GameOverCheckResult
import react.StateSetter
import view.states.GameOverView

class GameOverState(
    model: GameModel,
    setGameState: StateSetter<GameState?>,
    setWaitingForServer: StateSetter<Boolean>,
    val gameOverCheckResult: GameOverCheckResult
) : GameState(model, GameOverView, setGameState, setWaitingForServer) {

    override fun isGameOver(): Boolean = true

}
