package state

import model.GameModel
import react.StateSetter
import view.components.PauseMenu

class PauseGameState(gameModel: GameModel, setGameState: StateSetter<GameState?>, setWaitingForServer: StateSetter<Boolean>)
        : GameState(gameModel, PauseMenu, setGameState, setWaitingForServer) {

    override fun play() {
        setGameState(PlayingGameState(gameModel!!, setGameState, setWaitingForServer, false))
    }
}