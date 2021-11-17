package components

import state.GameState
import state.PlayingGameState
import getMachinePlay
import kotlinx.coroutines.*
import kotlinx.css.*
import model.GamePiece
import model.GamePlayer
import model.GameModel
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import styled.*
import model.utilities.Position

private val scope = MainScope()

val GameBoard = fc<Props> {
    // either repeatedly assign a new state to this variable or force the component to rerender manually
    var gameState: GameState by useState(PlayingGameState(GameModel(GamePiece.X)))
    var waitingForServer by useState(false)

    val makePlay: (Int, Int) -> ((Event) -> Unit) = { x, y -> {
            gameState = (gameState.clickSquare(GamePlayer.HUMAN, Position(x, y)) ?: error("Invalid play!"))
                .also {
                    if (!it.isGameOver()) {
                        waitingForServer = true
                        scope.launch {
                            delay(1000)
                            gameState = gameState.clickSquare(GamePlayer.MACHINE, getMachinePlay(it.gameModel))!!
                            waitingForServer = false
                        }
                    }
                }
        }
    }

    if (gameState.isGameOver()) {
        p {
            +"${gameState.getGameOverType()} wins!"
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

    for ((y, line) in gameState.gameModel.gameBoard.withIndex()) {
        div {
            key = y.toString()
            for ((x, gamePiece) in line.withIndex()) {
                child(GameBoardSquare) {
                    key = x.toString() + y.toString()
                    attrs {
                        piece = gamePiece
                        onClickFunction = makePlay(x, y)
                        canClick = !gameState.isGameOver() && !waitingForServer
                    }
                }
            }
        }
    }
}
