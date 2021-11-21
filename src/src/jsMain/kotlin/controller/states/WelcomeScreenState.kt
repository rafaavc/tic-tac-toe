package controller.states

import controller.GameState
import react.StateSetter
import view.states.WelcomeScreenView

class WelcomeScreenState(
    setGameState: StateSetter<GameState?>,
    setWaitingForServer: StateSetter<Boolean>
) : GameState(null, WelcomeScreenView, setGameState, setWaitingForServer) {

    override fun play() {
        setGameState(PieceSelectionState(setGameState, setWaitingForServer))
    }

    override fun settings() {
        setGameState(SettingsState(setGameState, setWaitingForServer))
    }

}
