package view.components

import getMachinePlay
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.css.Color
import kotlinx.css.color
import kotlinx.html.js.onClickFunction
import model.GamePlayer
import model.utilities.Position
import org.w3c.dom.events.Event
import react.dom.attrs
import react.dom.button
import react.dom.p
import react.fc
import react.useState
import styled.css
import styled.styledP
import view.ViewProps

private val scope = MainScope()

val PlayingGame = fc<ViewProps> { props ->
    // either repeatedly assign a new state to this variable or force the component to rerender manually
    var waitingForServer by useState(false)
    val gameState = props.gameState

    val makePlay: (Int, Int) -> ((Event) -> Unit) = { x, y -> {
            (gameState.clickSquare(GamePlayer.HUMAN, Position(x, y)) ?: error("Invalid play!"))
                .also { // TODO this should be part of the controller / state
                    if (!it.isGameOver()) {
                        waitingForServer = true
                        scope.launch {
                            gameState.clickSquare(GamePlayer.MACHINE, getMachinePlay(it.gameModel!!))
                            waitingForServer = false
                        }
                    }
                }
        }
    }

    val canClick: (Int, Int) -> Boolean = { x, y ->
        !gameState.isGameOver() && !waitingForServer && gameState.canClickSquare(Position(x, y))
    }

    if (!waitingForServer)
        button {
            attrs {
                onClickFunction = { _ -> gameState.pause() }
            }
            +"Pause"
        }

    if (waitingForServer) {
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

    child(GameBoard) {
        attrs {
            getOnClickFunction = makePlay
            lastPlay = gameState.getLastPlay()
            this.canClick = canClick
            this.gameState = gameState
        }
    }
}
