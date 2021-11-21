package view.states

import kotlinx.html.js.onClickFunction
import react.dom.attrs
import react.dom.button
import react.dom.p
import react.fc
import view.ViewProps

val SettingsView = fc<ViewProps> { props ->
    val gameState = props.gameState

    p {
        +"Settings"
    }

    button {
        attrs {
            onClickFunction = { gameState.quit() }
        }
        +"Go back"
    }
}
