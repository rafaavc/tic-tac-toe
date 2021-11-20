package view

import react.Props
import controller.GameState

external interface ViewProps : Props {
    var gameState: GameState
    var waitingForServer: Boolean
}
