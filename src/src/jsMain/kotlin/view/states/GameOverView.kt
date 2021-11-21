package view.states

import controller.states.GameOverState
import react.dom.p
import react.fc
import rsuite.RSuiteButton
import view.ViewProps
import view.components.GameBar
import view.components.GameBoard
import view.defaultButtonSize

val GameOverView = fc<ViewProps> { props ->
    val gameState = props.gameState as GameOverState


    child(GameBar) {
        child(RSuiteButton) {
            attrs {
                onClick = { gameState.quit() }
                size = defaultButtonSize
            }
            +"Quit game"
        }
    }

    p {
        +"${gameState.gameOverCheckResult.type}"
    }

    child(GameBoard) {
        attrs {
            this.gameState = gameState
            winningPieces = gameState.gameOverCheckResult.winningPieces
        }
    }
}
