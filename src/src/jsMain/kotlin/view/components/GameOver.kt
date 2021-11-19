package view.components

import kotlinx.html.js.onClickFunction
import react.dom.attrs
import react.dom.button
import react.dom.p
import react.fc
import view.ViewProps

val GameOver = fc<ViewProps> { props ->
    val gameState = props.gameState

    button {
        attrs {
            onClickFunction = { _ -> gameState.quit() }
        }
        +"Quit game"
    }

    p {
        +"${gameState.getGameOverType()}"
    }

    child(GameBoard) {
        attrs {
            this.gameState = gameState
        }
    }
}
