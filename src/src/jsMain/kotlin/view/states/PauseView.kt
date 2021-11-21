package view.states

import react.createElement
import react.fc
import rsuite.PlayIcon
import rsuite.RSuiteButton
import rsuite.RSuiteIconButton
import view.ViewProps
import view.components.CustomIcon
import view.components.GameBar
import view.components.GameBoard
import view.defaultButtonSize

val PauseMenuView = fc<ViewProps> { props ->
    val gameState = props.gameState

    child(GameBar) {
        child(RSuiteIconButton) {
            attrs {
                size = defaultButtonSize
                circle = true
                icon = createElement(CustomIcon(PlayIcon))
                onClick = { gameState.play() }
            }
        }
        child(RSuiteButton) {
            attrs {
                size = defaultButtonSize
                onClick = { gameState.quit() }
            }
            +"Quit game"
        }
    }

    child(GameBoard) {
        attrs {
            inactive = true
            lastPlay = gameState.model!!.lastPlay
            this.gameState = gameState
        }
    }
}