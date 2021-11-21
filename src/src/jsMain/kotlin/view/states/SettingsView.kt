package view.states

import react.dom.p
import react.fc
import rsuite.RSuiteButton
import rsuite.RSuiteSize
import view.ViewProps

val SettingsView = fc<ViewProps> { props ->
    val gameState = props.gameState

    p {
        +"Settings"
    }

    child(RSuiteButton) {
        attrs {
            size = RSuiteSize.LG
            onClick = { gameState.quit() }
        }
        +"Go back"
    }
}
