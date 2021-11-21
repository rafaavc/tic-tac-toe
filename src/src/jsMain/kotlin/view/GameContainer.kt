package view

import react.*
import controller.GameState
import controller.states.WelcomeScreenState

val GameContainer = fc<Props> {
    val (gameState, setGameState) = useState<GameState?>(null)
    val (waitingForServer, setWaitingForServer) = useState(false)
    useEffectOnce {
        setGameState(WelcomeScreenState(setGameState, setWaitingForServer))
    }

    if (gameState == null) return@fc

    child(gameState.gameView) {
        attrs {
            this.gameState = gameState
            this.waitingForServer = waitingForServer
        }
    }
}
