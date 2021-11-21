package view.states

import react.fc
import rsuite.RSuiteButton
import view.ViewProps
import view.components.GameBar
import view.defaultButtonSize

val SettingsView = fc<ViewProps> { props ->
    val gameState = props.gameState

    child(GameBar) {
        child(RSuiteButton) {
            attrs {
                size = defaultButtonSize
                onClick = { gameState.quit() }
            }
            +"Go back"
        }
    }
}
