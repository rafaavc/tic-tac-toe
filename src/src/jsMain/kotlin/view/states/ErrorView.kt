package view.states

import controller.states.ErrorState
import kotlinx.css.Color
import kotlinx.css.LinearDimension
import kotlinx.css.color
import kotlinx.css.marginBottom
import react.fc
import rsuite.RSuiteButton
import styled.css
import styled.styledP
import view.ViewProps
import view.defaultButtonSize

val ErrorView = fc<ViewProps> { props ->
    val gameState = props.gameState as ErrorState

    styledP {
        css {
            color = Color("rgb(210, 48, 12)")
            marginBottom = LinearDimension("1rem")
        }
        +(if (gameState.errorMessage != null) "Error: ${gameState.errorMessage}." else "Critical error.")
    }

    child(RSuiteButton) {
        attrs {
            onClick = { gameState.quit() }
            size = defaultButtonSize
        }
        +"Go back to main screen"
    }
}
