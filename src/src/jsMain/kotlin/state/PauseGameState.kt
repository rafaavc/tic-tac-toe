package state

import model.GameModel
import react.StateSetter
import view.components.PauseMenu

class PauseGameState(gameModel: GameModel, setGameState: StateSetter<GameState?>) : GameState(gameModel, PauseMenu, setGameState) {
    override fun play() {
        setGameState(PlayingGameState(gameModel!!, setGameState, false))
    }
}