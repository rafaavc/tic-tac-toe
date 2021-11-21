package view.states

import model.GamePiece
import react.fc
import rsuite.RSuiteButton
import view.ViewProps
import view.components.GameBar
import view.defaultButtonSize

val PieceSelectionView = fc<ViewProps> { props ->
    val gameState = props.gameState

    child(GameBar) {
        child(RSuiteButton) {
            attrs {
                size = defaultButtonSize
                onClick = { gameState.choosePlayer(GamePiece.X) }
            }
            +"Player X"
        }
        child(RSuiteButton) {
            attrs {
                size = defaultButtonSize
                onClick = { gameState.choosePlayer(GamePiece.O) }
            }
            +"Player O"
        }
    }
}