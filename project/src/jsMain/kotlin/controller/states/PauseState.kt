package controller.states

import controller.GameState
import controller.GameStateFactory
import react.StateSetter
import view.states.PauseView

class PauseState(
    private val gameState: GameState,
    setGameState: StateSetter<GameState?>,
    gameStateFactory: GameStateFactory
) : GameState(gameState.model, PauseView, setGameState, gameStateFactory) {

    override fun play() {
        setGameState(gameState)
    }

}