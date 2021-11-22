package controller.states

import controller.GameSettings
import controller.GameState
import controller.GameStateFactory
import react.StateSetter
import view.states.WelcomeScreenView

class WelcomeScreenState(
    setGameState: StateSetter<GameState?>,
    setWaitingForServer: StateSetter<Boolean>,
    gameStateFactory: GameStateFactory,
    val settings: GameSettings
) : GameState(null, WelcomeScreenView, setGameState, setWaitingForServer, gameStateFactory) {

    override fun play() {
        setGameState(gameStateFactory.createPlayingState())
    }

}
