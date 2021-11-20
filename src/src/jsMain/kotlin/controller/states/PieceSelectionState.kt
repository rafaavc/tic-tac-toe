package controller.states

import controller.GameState
import controller.move.HvMStrategy
import model.GameModel
import model.GamePiece
import react.StateSetter
import view.states.PieceSelection

class PieceSelectionState(
    setGameState: StateSetter<GameState?>,
    setWaitingForServer: StateSetter<Boolean>
) : GameState(null, PieceSelection, setGameState, setWaitingForServer) {

    override fun choosePlayer(piece: GamePiece) {
        val model = GameModel(piece)
        val moveStrategy = HvMStrategy(model, setGameState, setWaitingForServer)
        setGameState(PlayingState(model, setGameState, setWaitingForServer, moveStrategy))
    }

}