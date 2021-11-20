package view.states

import controller.states.GameOverState
import kotlinx.html.js.onClickFunction
import react.dom.attrs
import react.dom.button
import react.dom.p
import react.fc
import view.ViewProps
import view.components.GameBoard

val GameOver = fc<ViewProps> { props ->
    val gameState = props.gameState as GameOverState

    button {
        attrs {
            onClickFunction = { _ -> gameState.quit() }
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
