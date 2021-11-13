package components

import game.GamePiece
import game.GamePlayer
import game.GameState
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.css.Color
import kotlinx.css.color
import makeMachinePlay
import react.*
import react.dom.*
import styled.css
import styled.styledP
import utilities.Position

private val scope = MainScope()

val GameBoard = fc<Props> {
    var gameState by useState(GameState(GamePiece.X))
    var waitingForServer by useState(false)

    val makePlay: (Int, Int) -> Unit = { x, y ->
        gameState = (gameState.makePlay(GamePlayer.HUMAN, Position(x, y)) ?: error("Invalid play!"))
            .also {
                if (!it.gameOver) {
                    waitingForServer = true
                    scope.launch {
                        delay(1000)
                        gameState = makeMachinePlay(it)
                        waitingForServer = false
                    }
                }
            }
    }

    if (gameState.gameOver) {
        p {
            +"GameOver"
        }
    }

    else if (waitingForServer) {
        p {
            +"Waiting..."
        }
    }

    else styledP {
        +"."
        css {
            color = Color.transparent
        }
    }

    for ((y, line) in gameState.gameBoard.withIndex()) {
        div {
            for ((x, gamePiece) in line.withIndex()) {
                child(GameBoardSquare) {
                    attrs {
                        piece = gamePiece
                        onClickFunction = { makePlay(x, y) }
                        canClick = !gameState.gameOver && !waitingForServer
                    }
                }
            }
        }
    }
}
