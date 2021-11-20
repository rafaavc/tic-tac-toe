package view.states

import kotlinx.html.js.onClickFunction
import react.dom.attrs
import react.dom.button
import react.fc
import view.ViewProps

val WelcomeScreen = fc<ViewProps> { props ->
    val gameState = props.gameState

    button {
        attrs {
            onClickFunction = { _ -> gameState.play() }
        }
        +"Play"
    }
}
