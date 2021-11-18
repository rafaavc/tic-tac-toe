package state

import model.GameModel
import model.GamePiece
import react.StateSetter
import view.components.PieceSelection

class PieceSelectionState(setGameState: StateSetter<GameState?>) : GameState(null, PieceSelection, setGameState) {
    override fun choosePlayer(piece: GamePiece) {
        setGameState(PlayingGameState(GameModel(piece), setGameState))
    }

}