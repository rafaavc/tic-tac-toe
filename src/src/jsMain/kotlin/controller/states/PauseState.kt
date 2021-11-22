package controller.states

import controller.GameState
import controller.GameStateFactory
import controller.move.MoveStrategy
import model.GameModel
import react.StateSetter
import view.states.PauseView

class PauseState(
    private val gameState: GameState,
    setGameState: StateSetter<GameState?>,
    setWaitingForServer: StateSetter<Boolean>,
    gameStateFactory: GameStateFactory
) : GameState(gameState.model, PauseView, setGameState, setWaitingForServer, gameStateFactory) {

    override fun play() {
        setGameState(gameState)
    }

}