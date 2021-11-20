package state

import react.StateSetter
import view.components.WelcomeScreen

class WelcomeScreenState(setGameState: StateSetter<GameState?>, setWaitingForServer: StateSetter<Boolean>)
        : GameState(null, WelcomeScreen, setGameState, setWaitingForServer) {

    override fun play() {
        setGameState(PieceSelectionState(setGameState, setWaitingForServer))
    }
}
