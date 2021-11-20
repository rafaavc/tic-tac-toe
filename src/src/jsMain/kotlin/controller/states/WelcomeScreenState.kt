package controller.states

import controller.GameState
import react.StateSetter
import view.states.WelcomeScreen

class WelcomeScreenState(
    setGameState: StateSetter<GameState?>,
    setWaitingForServer: StateSetter<Boolean>
) : GameState(null, WelcomeScreen, setGameState, setWaitingForServer) {

    override fun play() {
        setGameState(PieceSelectionState(setGameState, setWaitingForServer))
    }

}
