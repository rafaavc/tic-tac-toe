package controller.states

import controller.GameState
import controller.GameStateFactory
import react.StateSetter
import view.states.SettingsView

class SettingsState(
    setGameState: StateSetter<GameState?>,
    setWaitingForServer: StateSetter<Boolean>,
    gameStateFactory: GameStateFactory,
) : GameState(null, SettingsView, setGameState, setWaitingForServer, gameStateFactory)
