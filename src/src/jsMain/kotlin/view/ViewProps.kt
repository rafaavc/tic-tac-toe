package view

import controller.GameSettings
import react.Props
import controller.GameState

external interface ViewProps : Props {
    var gameState: GameState
    var waitingForServer: Boolean
    var settings: GameSettings
}
