package view.states

import model.GamePiece
import react.fc
import rsuite.RSuiteButton
import rsuite.RSuiteSize
import view.ViewProps

val PieceSelectionView = fc<ViewProps> { props ->
    val gameState = props.gameState

    child(RSuiteButton) {
        attrs {
            size = RSuiteSize.LG
            onClick = { gameState.choosePlayer(GamePiece.X) }
        }
        +"Player X"
    }
    child(RSuiteButton) {
        attrs {
            size = RSuiteSize.LG
            onClick = { gameState.choosePlayer(GamePiece.O) }
        }
        +"Player O"
    }
}