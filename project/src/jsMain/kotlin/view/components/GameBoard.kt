package view.components

import kotlinx.css.opacity
import model.GamePiece
import model.utilities.Position
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import styled.css
import styled.styledDiv
import view.ViewProps

external interface GameBoardProps : ViewProps {
    var getOnClickFunction: ((Int, Int) -> ((Event) -> Unit))?
    var canClick: ((Int, Int) -> Boolean)?
    var winningPieces: Set<Position>?
    var showSuccess: Boolean?
    var lastPlay: Position?
    var inactive: Boolean?
    var currentPlayerPiece: GamePiece
}

val GameBoard = fc<GameBoardProps> { props ->
    val gameState = props.gameState

    styledDiv {
        css {
            opacity = if (props.inactive == true) 0.5 else 1
        }

        for ((y, line) in gameState.model!!.gameBoard.withIndex()) {
            div {
                key = y.toString()

                for ((x, gamePiece) in line.withIndex()) {
                    child(GameBoardSquare) {
                        key = x.toString() + y.toString()

                        attrs {
                            piece = gamePiece
                            currentPlayerPiece = props.currentPlayerPiece

                            won = props.winningPieces != null && Position(x, y) in props.winningPieces!!
                            showSuccess = props.showSuccess
                            lastPlay = Position(x, y) == props.lastPlay

                            onClickFunction = props.getOnClickFunction?.run { this(x, y) } ?: {}
                            canClick = props.canClick?.run { this(x, y) } ?: false
                        }
                    }
                }
            }
        }
    }
}
