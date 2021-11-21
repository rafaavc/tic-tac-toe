package view.states

import react.createElement
import react.fc
import view.ViewProps
import rsuite.*
import view.components.CustomIcon
import view.components.GameBar
import view.defaultButtonSize

val WelcomeScreenView = fc<ViewProps> { props ->
    val gameState = props.gameState

    child(GameBar) {
        child(RSuiteButton) {
            attrs {
                size = defaultButtonSize
                onClick = { gameState.play() }
            }
            +"Play"
        }

        child(RSuiteIconButton) {
            attrs {
                size = defaultButtonSize
                onClick = { gameState.settings() }
                icon = createElement(CustomIcon(SettingsIcon))
                circle = true
            }
        }
    }
}
