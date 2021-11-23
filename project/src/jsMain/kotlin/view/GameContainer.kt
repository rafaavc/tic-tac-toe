package view

import controller.GameSettings
import react.*
import controller.GameState
import controller.GameStateFactory
import kotlinx.css.*
import styled.css
import styled.styledDiv
import styled.styledH1

val GameContainer = fc<Props> {
    val (gameState, setGameState) = useState<GameState?>(null)
    val (waitingForServer, setWaitingForServer) = useState(false)

    useEffectOnce {
        setGameState(GameStateFactory(GameSettings(), setGameState, setWaitingForServer).createPlayingState())
    }

    if (gameState == null) return@fc

    styledDiv {
        css {
            put("minWidth", "min(27rem, 90vw)")
            maxWidth = LinearDimension("90vw")
            marginTop = LinearDimension("3rem")
        }

        styledH1 {
            css {
                marginBottom = LinearDimension(marginMedium)
                fontSize = LinearDimension("3rem")
            }
            +"TicTacToe"
        }

        child(gameState.gameView) {
            attrs {
                this.settings = settings
                this.gameState = gameState
                this.waitingForServer = waitingForServer
            }
        }
    }
}
