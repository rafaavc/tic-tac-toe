package view.states

import controller.states.WelcomeScreenState
import model.GamePiece
import react.fc
import react.useState
import view.ViewProps
import rsuite.*
import view.components.Container
import view.components.blueColor
import view.defaultButtonSize

val WelcomeScreenView = fc<ViewProps> { props ->
    val gameState = props.gameState as WelcomeScreenState

    var player1Piece by useState(gameState.settings.player1Piece)
    var boardSize by useState(gameState.settings.boardSize)
    var target by useState(gameState.settings.target)
    val timeToThink by useState(gameState.settings.timeToThink)


    child(Container) {
        attrs {
            flex = false
        }

        for (piece in arrayOf(GamePiece.O, GamePiece.X))
            child(RSuiteButton) {
                attrs {
                    size = defaultButtonSize
                    onClick = { player1Piece = piece }
                    active = player1Piece == piece
                }
                +piece.value
            }
    }

    child(Container) {
        attrs {
            flex = false
        }

        child(RSuiteSlider) {
            attrs {
                defaultValue = boardSize.toDouble()
                min = 3.0
                max = 10.0
                step = 1.0
                onChange = { value -> boardSize = value.toInt() }
            }
        }
    }

    child(Container) {
        attrs {
            flex = false
        }

        child(RSuiteSlider) {
            attrs {
                defaultValue = target.toDouble()
                min = 3.0
                max = 10.0
                step = 1.0
                onChange = { value -> target = value.toInt() }
            }
        }
    }

    child(RSuiteButton) {
        attrs {
            size = defaultButtonSize
            color = "blue"
            appearance = "primary"
            onClick = {
                gameState.settings.set(player1Piece, boardSize, target, timeToThink)
                gameState.play()
            }
        }
        +"Play"
    }
}
