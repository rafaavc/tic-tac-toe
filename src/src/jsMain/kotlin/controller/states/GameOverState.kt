package controller.states

import controller.GameState
import controller.GameStateFactory
import model.GameModel
import model.GameOverCheckResult
import react.StateSetter
import view.states.GameOverView

class GameOverState(
    model: GameModel,
    setGameState: StateSetter<GameState?>,
    setWaitingForServer: StateSetter<Boolean>,
    gameStateFactory: GameStateFactory,
    val gameOverCheckResult: GameOverCheckResult
) : GameState(model, GameOverView, setGameState, setWaitingForServer, gameStateFactory) {

    override fun isGameOver(): Boolean = true

}
