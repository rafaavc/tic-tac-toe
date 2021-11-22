package controller.states

import controller.GameState
import controller.GameStateFactory
import react.StateSetter
import view.states.WelcomeScreenView

class WelcomeScreenState(
    setGameState: StateSetter<GameState?>,
    setWaitingForServer: StateSetter<Boolean>,
    gameStateFactory: GameStateFactory
) : GameState(null, WelcomeScreenView, setGameState, setWaitingForServer, gameStateFactory) {

    override fun play() {
        setGameState(gameStateFactory.createPieceSelectionState())
    }

    override fun settings() {
        setGameState(gameStateFactory.createSettingsState())
    }

}
