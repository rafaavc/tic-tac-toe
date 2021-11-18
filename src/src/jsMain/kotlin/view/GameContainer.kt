package view

import react.*
import state.GameState
import state.WelcomeScreenState

val GameContainer = fc<Props> {
    val (gameState, setGameState) = useState<GameState?>(null)
    useEffectOnce {
        setGameState(WelcomeScreenState(setGameState))
    }

    if (gameState == null) return@fc

    child(gameState.gameView) {
        attrs {
            this.gameState = gameState
        }
    }
}
