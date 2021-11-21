package controller.states

import controller.GameState
import react.StateSetter
import view.states.SettingsView

class SettingsState(
    setGameState: StateSetter<GameState?>,
    setWaitingForServer: StateSetter<Boolean>
) : GameState(null, SettingsView, setGameState, setWaitingForServer)
