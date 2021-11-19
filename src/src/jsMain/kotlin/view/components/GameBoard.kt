package view.components

import org.w3c.dom.events.Event
import react.*
import react.dom.*
import view.ViewProps

external interface GameBoardProps : ViewProps {
    var getOnClickFunction: ((Int, Int) -> ((Event) -> Unit))?
    var canClick: ((Int, Int) -> Boolean)?
}

val GameBoard = fc<GameBoardProps> { props ->
    val gameState = props.gameState

    for ((y, line) in gameState.gameModel!!.gameBoard.withIndex()) {
        div {
            key = y.toString()
            for ((x, gamePiece) in line.withIndex()) {
                child(GameBoardSquare) {
                    key = x.toString() + y.toString()
                    attrs {
                        piece = gamePiece
                        onClickFunction = props.getOnClickFunction?.run { this(x, y) } ?: {}
                        canClick = props.canClick?.run { this(x, y) } ?: false
                    }
                }
            }
        }
    }
}
