package view.components

import model.utilities.Position
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import view.ViewProps

external interface GameBoardProps : ViewProps {
    var getOnClickFunction: ((Int, Int) -> ((Event) -> Unit))?
    var canClick: ((Int, Int) -> Boolean)?
    var winningPieces: Set<Position>?
    var lastPlay: Position?
}

val GameBoard = fc<GameBoardProps> { props ->
    val gameState = props.gameState

    for ((y, line) in gameState.model!!.gameBoard.withIndex()) {
        div {
            key = y.toString()
            for ((x, gamePiece) in line.withIndex()) {
                child(GameBoardSquare) {
                    key = x.toString() + y.toString()
                    attrs {
                        piece = gamePiece
                        onClickFunction = props.getOnClickFunction?.run { this(x, y) } ?: {}
                        canClick = props.canClick?.run { this(x, y) } ?: false
                        won = props.winningPieces != null && Position(x, y) in props.winningPieces!!
                        lastPlay = Position(x, y) == props.lastPlay
                    }
                }
            }
        }
    }
}
