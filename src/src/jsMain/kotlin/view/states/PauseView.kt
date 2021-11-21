package view.states

import kotlinx.html.js.onClickFunction
import react.dom.attrs
import react.dom.button
import react.fc
import view.ViewProps

val PauseMenuView = fc<ViewProps> { props ->
    val gameState = props.gameState

    button {
        attrs {
            onClickFunction = { gameState.play() }
        }
        +"Resume game"
    }
    button {
        attrs {
            onClickFunction = { gameState.quit() }
        }
        +"Quit game"
    }
}