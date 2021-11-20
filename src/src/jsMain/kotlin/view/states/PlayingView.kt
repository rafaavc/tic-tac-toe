package view.states

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
import view.components.GameBoard

val PlayingGame = fc<ViewProps> { props ->
    val gameState = props.gameState

    if (gameState.model == null) error("The game model is not set in the current playing state!")

    val makePlay: (Int, Int) -> ((Event) -> Unit) = { x, y -> {
            gameState.makeMove(GamePlayer.PLAYER1, Position(x, y)) ?: error("Invalid play!")
        }
    }

    val canClick: (Int, Int) -> Boolean = { x, y ->
        !props.waitingForServer && gameState.canMakeMove(Position(x, y))
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
            lastPlay = gameState.model.lastPlay
            this.canClick = canClick
            this.gameState = gameState
        }
    }
}
