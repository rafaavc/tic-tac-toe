package view.states

import controller.states.GameOverState
import react.dom.p
import react.fc
import rsuite.RSuiteButton
import rsuite.RSuiteSize
import view.ViewProps
import view.components.GameBoard

val GameOverView = fc<ViewProps> { props ->
    val gameState = props.gameState as GameOverState

    child(RSuiteButton) {
        attrs {
            onClick = { gameState.quit() }
            size = RSuiteSize.LG
        }
        +"Quit game"
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
