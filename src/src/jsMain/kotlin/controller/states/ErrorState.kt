package controller.states

import controller.GameState
import react.StateSetter
import view.states.ErrorView

class ErrorState(
    setGameState: StateSetter<GameState?>,
    setWaitingForServer: StateSetter<Boolean>,
    val errorMessage: String?
) : GameState(null, ErrorView, setGameState, setWaitingForServer)
