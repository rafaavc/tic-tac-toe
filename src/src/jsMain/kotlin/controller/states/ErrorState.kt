package controller.states

import controller.GameState
import controller.GameStateFactory
import react.StateSetter
import view.states.ErrorView

class ErrorState(
    setGameState: StateSetter<GameState?>,
    setWaitingForServer: StateSetter<Boolean>,
    gameStateFactory: GameStateFactory,
    val errorMessage: String?
) : GameState(null, ErrorView, setGameState, setWaitingForServer, gameStateFactory)
