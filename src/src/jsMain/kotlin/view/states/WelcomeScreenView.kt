package view.states

import controller.states.WelcomeScreenState
import model.GamePiece
import react.fc
import react.useState
import view.ViewProps
import rsuite.*
import view.components.Container
import view.components.LabelField
import view.defaultButtonSize
import view.marginHuge
import view.marginLarge

val WelcomeScreenView = fc<ViewProps> { props ->
    val gameState = props.gameState as WelcomeScreenState

    var player1Piece by useState(gameState.settings.player1Piece)
    var boardSize by useState(gameState.settings.boardSize)
    var target by useState(gameState.settings.target)
    var timeToThink by useState(gameState.settings.timeToThink)

    child(Container) {
        attrs {
            flex = false
            marginBottom = marginLarge
            marginTop = marginHuge
        }

        child(LabelField) {
            attrs {
                label = "Player ($player1Piece)"
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
    }

    child(Container) {
        attrs {
            flex = false
            marginBottom = marginLarge
        }

        child(LabelField) {
            attrs {
                label = "Board size (${boardSize}x$boardSize)"
            }

            child(RSuiteSlider) {
                attrs {
                    value = boardSize.toDouble()
                    min = 3.0
                    max = 10.0
                    step = 1.0
                    onChange = { value ->
                        boardSize = value.toInt()
                        if (target > value) target = value.toInt()
                    }
                }
            }
        }
    }

    child(Container) {
        attrs {
            flex = false
            marginBottom = marginLarge
        }

        child(LabelField) {
            attrs {
                label = "How many pieces in a row to win ($target)"
            }

            child(RSuiteSlider) {
                attrs {
                    value = target.toDouble()
                    min = 3.0
                    max = boardSize.toDouble()
                    step = 1.0
                    onChange = { value -> target = value.toInt() }
                }
            }
        }
    }

    child(Container) {
        attrs {
            flex = false
            marginBottom = marginLarge
        }

        child(LabelField) {
            attrs {
                label = "Time for the opponent to think (${timeToThink}s)"
            }

            child(RSuiteSlider) {
                attrs {
                    value = timeToThink
                    min = 0.5
                    max = 10.0
                    step = 0.5
                    onChange = { value -> timeToThink = value }
                }
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
