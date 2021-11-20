package controller.states

import controller.GameState
import model.GameModel
import react.StateSetter
import view.states.PauseMenu

class PauseState(
    model: GameModel,
    setGameState: StateSetter<GameState?>,
    setWaitingForServer: StateSetter<Boolean>
) : GameState(model, PauseMenu, setGameState, setWaitingForServer) {

    override fun play() {
        setGameState(PlayingGameState(model!!, setGameState, setWaitingForServer, false))
    }
}