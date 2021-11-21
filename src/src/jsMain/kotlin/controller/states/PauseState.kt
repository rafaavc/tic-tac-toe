package controller.states

import controller.GameState
import controller.move.MoveStrategy
import model.GameModel
import react.StateSetter
import view.states.PauseView

class PauseState(
    model: GameModel,
    setGameState: StateSetter<GameState?>,
    setWaitingForServer: StateSetter<Boolean>,
    private val moveStrategy: MoveStrategy,
) : GameState(model, PauseView, setGameState, setWaitingForServer) {

    override fun play() {
        setGameState(PlayingState(model!!, setGameState, setWaitingForServer, moveStrategy))
    }

}