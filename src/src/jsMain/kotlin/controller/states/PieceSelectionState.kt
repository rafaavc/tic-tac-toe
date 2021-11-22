package controller.states

import controller.GameState
import controller.GameStateFactory
import controller.move.HvMStrategy
import model.GameModel
import model.GamePiece
import react.StateSetter
import view.states.PieceSelectionView

class PieceSelectionState(
    setGameState: StateSetter<GameState?>,
    setWaitingForServer: StateSetter<Boolean>,
    gameStateFactory: GameStateFactory
) : GameState(null, PieceSelectionView, setGameState, setWaitingForServer, gameStateFactory) {

    override fun choosePlayer(piece: GamePiece) {
        val model = GameModel(piece)
        val moveStrategy = HvMStrategy(model, setGameState, setWaitingForServer, gameStateFactory)
        setGameState(gameStateFactory.createPlayingState(model, moveStrategy))
    }

}