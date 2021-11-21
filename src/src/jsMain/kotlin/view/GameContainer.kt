package view

import react.*
import controller.GameState
import controller.states.WelcomeScreenState
import kotlinx.css.*
import rsuite.RSuiteSize
import styled.css
import styled.styledDiv
import styled.styledH1

val defaultButtonSize = RSuiteSize.MD.value

val GameContainer = fc<Props> {
    val (gameState, setGameState) = useState<GameState?>(null)
    val (waitingForServer, setWaitingForServer) = useState(false)

    useEffectOnce {
        setGameState(WelcomeScreenState(setGameState, setWaitingForServer))
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
                marginBottom = LinearDimension("1.5rem")
                fontSize = LinearDimension("3rem")
            }
            +"TicTacToe"
        }
        child(gameState.gameView) {
            attrs {
                this.gameState = gameState
                this.waitingForServer = waitingForServer
            }
        }
    }
}
