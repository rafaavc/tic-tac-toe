package view.components

import kotlinx.html.js.onClickFunction
import model.GamePiece
import react.dom.attrs
import react.dom.button
import react.fc
import view.ViewProps

val PieceSelection = fc<ViewProps> { props ->
    val gameState = props.gameState

    button {
        attrs {
            onClickFunction = { _ -> gameState.choosePlayer(GamePiece.X) }
        }
        +"Player X"
    }
    button {
        attrs {
            onClickFunction = { _ -> gameState.choosePlayer(GamePiece.O) }
        }
        +"Player O"
    }
}