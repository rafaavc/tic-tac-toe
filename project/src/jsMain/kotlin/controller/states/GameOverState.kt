package controller.states

import controller.GameState
import controller.GameStateFactory
import model.GameModel
import controller.GameOverCheckResult
import react.StateSetter
import view.states.GameOverView

class GameOverState(
    model: GameModel,
    setGameState: StateSetter<GameState?>,
    gameStateFactory: GameStateFactory,
    val gameOverCheckResult: GameOverCheckResult
) : GameState(model, GameOverView, setGameState, gameStateFactory) {

    override fun isGameOver(): Boolean = true

}
