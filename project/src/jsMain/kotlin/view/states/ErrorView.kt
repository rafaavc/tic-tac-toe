package view.states

import controller.states.ErrorState
import kotlinx.css.Color
import kotlinx.css.LinearDimension
import kotlinx.css.color
import kotlinx.css.marginBottom
import react.fc
import view.externals.RSuiteButton
import styled.css
import styled.styledP
import view.ViewProps
import view.defaultButtonSize
import view.marginMedium
import view.redColor

val ErrorView = fc<ViewProps> { props ->
    val gameState = props.gameState as ErrorState

    styledP {
        css {
            color = Color(redColor)
            marginBottom = LinearDimension(marginMedium)
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
