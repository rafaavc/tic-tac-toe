package components

import csstype.FontFamily
import game.GamePiece
import game.GamePlayer
import game.GameState
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*
import styled.css
import styled.styledDiv
import utilities.Position


val GameBoard = fc<Props> {
    var gameState by useState(GameState(GamePiece.X))

    if (gameState.gameOver) {
        p {
            +"GameOver"
        }
    }

    for ((y, line) in gameState.gameBoard.withIndex()) {
        div {
            for ((x, piece) in line.withIndex()) {
                styledDiv {
                    +"${if (piece == GamePiece.EMPTY) '_' else piece}"

                    css {
                        display = Display.inlineBlock
                        fontFamily = FontFamily.monospace.toString()
                        fontSize = LinearDimension("2rem")
                        marginRight = LinearDimension("1rem")
                        cursor = Cursor.pointer
                        userSelect = UserSelect.none
                    }
                    attrs {
                        onClickFunction = {
                            gameState =
                                gameState.makePlay(GamePlayer.HUMAN, Position(x, y)) ?: error("Invalid play!")
                        }
                    }
                }
            }
        }
    }
}
