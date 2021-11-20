package view.components

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
import styled.css
import styled.styledP
import view.ViewProps

val PlayingGame = fc<ViewProps> { props ->
    // either repeatedly assign a new state to this variable or force the component to rerender manually
    val gameState = props.gameState

    val makePlay: (Int, Int) -> ((Event) -> Unit) = { x, y -> {
            gameState.clickSquare(GamePlayer.HUMAN, Position(x, y)) ?: error("Invalid play!")
        }
    }

    val canClick: (Int, Int) -> Boolean = { x, y ->
        !gameState.isGameOver() && !props.waitingForServer && gameState.canClickSquare(Position(x, y))
    }

    if (!props.waitingForServer)
        button {
            attrs {
                onClickFunction = { _ -> gameState.pause() }
            }
            +"Pause"
        }

    if (props.waitingForServer) {
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
