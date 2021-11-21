package view.states

import react.createElement
import react.fc
import view.ViewProps
import rsuite.*

val WelcomeScreenView = fc<ViewProps> { props ->
    val gameState = props.gameState

    child(RSuiteButton) {
        attrs {
            size = RSuiteSize.LG
            onClick = { gameState.play() }
        }
        +"Play"
    }

    child(RSuiteIconButton) {
        attrs {
            size = RSuiteSize.LG
            onClick = { gameState.settings() }
            icon = createElement(SettingsIcon)
            circle = true
        }
    }
}
