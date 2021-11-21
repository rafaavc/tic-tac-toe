package view.states

import model.GamePlayer
import model.utilities.Position
import org.w3c.dom.events.Event
import react.createElement
import react.fc
import rsuite.PauseIcon
import rsuite.RSuiteIconButton
import rsuite.RSuiteLoader
import rsuite.RSuiteSize
import view.ViewProps
import view.components.GameBoard

val PlayingGameView = fc<ViewProps> { props ->
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
        child(RSuiteIconButton) {
            attrs {
                size = RSuiteSize.LG
                circle = true
                icon = createElement(PauseIcon)
                onClick = { gameState.pause() }
            }
        }

    if (props.waitingForServer)
        child(RSuiteLoader) {
            attrs {
                content = "The opponent is thinking..."
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
