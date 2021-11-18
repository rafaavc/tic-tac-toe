package view.components

import getMachinePlay
import kotlinx.coroutines.*
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import model.GamePlayer
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import styled.*
import model.utilities.Position
import view.ViewProps

private val scope = MainScope()

val GameBoard = fc<ViewProps> { props ->
    // either repeatedly assign a new state to this variable or force the component to rerender manually
    var waitingForServer by useState(false)
    val gameState = props.gameState

    val makePlay: (Int, Int) -> ((Event) -> Unit) = { x, y -> {
            (gameState.clickSquare(GamePlayer.HUMAN, Position(x, y)) ?: error("Invalid play!"))
                .also {
                    if (!it.isGameOver()) {
                        waitingForServer = true
                        scope.launch {
                            delay(300)
                            gameState.clickSquare(GamePlayer.MACHINE, getMachinePlay(it.gameModel!!))
                            waitingForServer = false
                        }
                    }
                }
        }
    }

    button {
        attrs {
            onClickFunction = { _ -> gameState.pause() }
        }
        +"Pause"
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

    for ((y, line) in gameState.gameModel!!.gameBoard.withIndex()) {
        div {
            key = y.toString()
            for ((x, gamePiece) in line.withIndex()) {
                child(GameBoardSquare) {
                    key = x.toString() + y.toString()
                    attrs {
                        piece = gamePiece
                        onClickFunction = if (gameState.canClickSquare(Position(x, y))) makePlay(x, y) else { _ -> println("Can't click!") }
                        canClick = !gameState.isGameOver() && !waitingForServer
                    }
                }
            }
        }
    }
}
