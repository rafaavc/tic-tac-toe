package view.states

import react.createElement
import react.fc
import view.externals.PlayIcon
import view.externals.RSuiteButton
import view.externals.RSuiteIconButton
import view.ViewProps
import view.components.CustomIcon
import view.components.Container
import view.components.GameBoard
import view.defaultButtonSize

val PauseView = fc<ViewProps> { props ->
    val gameState = props.gameState

    child(Container) {
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