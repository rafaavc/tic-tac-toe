package view

import react.Props
import state.GameState

external interface ViewProps : Props {
    var gameState: GameState
}
