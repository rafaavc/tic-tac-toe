package state

import model.GameModel
import model.GamePiece
import react.StateSetter
import view.components.PieceSelection

class PieceSelectionState(setGameState: StateSetter<GameState?>, setWaitingForServer: StateSetter<Boolean>)
        : GameState(null, PieceSelection, setGameState, setWaitingForServer) {

    override fun choosePlayer(piece: GamePiece) {
        setGameState(PlayingGameState(GameModel(piece), setGameState, setWaitingForServer))
    }

}