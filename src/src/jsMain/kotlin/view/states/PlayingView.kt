package view.states

import kotlinx.css.*
import model.GamePlayer
import model.utilities.Position
import org.w3c.dom.events.Event
import react.createElement
import react.fc
import rsuite.PauseIcon
import rsuite.RSuiteIconButton
import rsuite.RSuiteLoader
import styled.css
import styled.styledSpan
import view.ViewProps
import view.components.CustomIcon
import view.components.Container
import view.components.GameBoard
import view.defaultButtonSize

val PlayingView = fc<ViewProps> { props ->
    val gameState = props.gameState

    val makePlay: (Int, Int) -> ((Event) -> Unit) = { x, y -> {
            gameState.makeMove(GamePlayer.PLAYER1, Position(x, y)) ?: error("Invalid play.")
        }
    }

    val canClick: (Int, Int) -> Boolean = { x, y ->
        !props.waitingForServer && gameState.canMakeMove(Position(x, y))
    }

    child(Container) {
        child(RSuiteIconButton) {
            attrs {
                size = defaultButtonSize
                circle = true
                icon = createElement(CustomIcon(PauseIcon))
                onClick = { gameState.pause() }
                disabled = props.waitingForServer
            }
        }
    }

    child(GameBoard) {
        attrs {
            getOnClickFunction = makePlay
            lastPlay = gameState.model!!.lastPlay
            currentPlayerPiece = gameState.getCurrentPlayerPiece()
            this.canClick = canClick
            this.gameState = gameState
        }
    }

    child(Container) {
        styledSpan {
            css {
                visibility = if (props.waitingForServer) Visibility.inherit else Visibility.hidden
            }
            child(RSuiteLoader) {
                attrs {
                    content = "Waiting for opponent..."
                }
            }
        }
        attrs {
            marginBottom = "0"
            marginTop = "2rem"
        }
    }

}
