package view.states

import react.fc
import rsuite.RSuiteButton
import rsuite.RSuiteSize
import view.ViewProps

val PauseMenuView = fc<ViewProps> { props ->
    val gameState = props.gameState

    child(RSuiteButton) {
        attrs {
            size = RSuiteSize.LG
            onClick = { gameState.play() }
        }
        +"Resume game"
    }
    child(RSuiteButton) {
        attrs {
            size = RSuiteSize.LG
            onClick = { gameState.quit() }
        }
        +"Quit game"
    }
}