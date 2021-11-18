package state

import react.StateSetter
import view.components.WelcomeScreen

class WelcomeScreenState(setGameState: StateSetter<GameState?>) : GameState(null, WelcomeScreen, setGameState) {
    override fun play() {
        setGameState(PieceSelectionState(setGameState))
    }
}
