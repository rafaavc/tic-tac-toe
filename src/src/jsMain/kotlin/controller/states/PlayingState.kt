package controller.states

import controller.GameState
import controller.GameStateFactory
import controller.move.MoveStrategy
import model.GameModel
import controller.GameOverCheckResult
import model.GamePiece
import model.GamePlayer
import model.utilities.Position
import react.StateSetter
import view.states.PlayingView

class PlayingState(
    model: GameModel,
    setGameState: StateSetter<GameState?>,
    setWaitingForServer: StateSetter<Boolean>,
    gameStateFactory: GameStateFactory,
    private val moveStrategy: MoveStrategy
) : GameState(model, PlayingView, setGameState, setWaitingForServer, gameStateFactory) {

    init {
        moveStrategy.makeFirstMove(this::getNextGameState)
    }

    private fun getNextGameState(gameOverCheckResult: GameOverCheckResult): GameState {
        if (gameOverCheckResult.isOver())
            return gameStateFactory.createGameOverState(model!!, gameOverCheckResult)
        return this
    }

    override fun getCurrentPlayerPiece(): GamePiece
        = moveStrategy.getCurrentPlayerPiece()

    override fun canMakeMove(squarePosition: Position): Boolean
        = moveStrategy.canMakeMove(squarePosition)

    override fun makeMove(player: GamePlayer, squarePosition: Position): GameState?
        = moveStrategy.makeMove(player, squarePosition, this::getNextGameState)

    override fun pause() {
        setGameState(gameStateFactory.createPauseState(this))
    }
}
