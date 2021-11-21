package controller.move

import controller.GameState
import controller.states.ErrorState
import getMachinePlay
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.GameModel
import model.GameOverCheckResult
import model.GamePiece
import model.GamePlayer
import model.utilities.Position
import react.StateSetter

private val scope = MainScope()

class HvMStrategy(
    model: GameModel,
    private val setGameState: StateSetter<GameState?>,
    private val setWaitingForServer: StateSetter<Boolean>
) : MoveStrategy(model) {

    private fun makeMachineMove(model: GameModel, getNextGameState: (GameOverCheckResult) -> GameState) {
        setWaitingForServer(true)
        scope.launch {
            try {
                if (model.isEmpty) delay(500)
                makeMove(GamePlayer.PLAYER2, getMachinePlay(model), getNextGameState)
            } catch (e: Throwable) {
                setGameState(ErrorState(setGameState, setWaitingForServer, e.message))
            }
            setWaitingForServer(false)
        }
    }

    override fun canMakeMove(squarePosition: Position) = model.isValidPlay(squarePosition)

    override fun makeMove(player: GamePlayer, squarePosition: Position,
                          getNextGameState: (GameOverCheckResult) -> GameState): GameState? {

        if (!model.makePlay(player, squarePosition)) return null

        val gameOverCheckResult = model.checkGameOver(player, squarePosition, getWinningPieces = true)

        return getNextGameState(gameOverCheckResult).also {
            setGameState(it)

            if (!it.isGameOver() && player == GamePlayer.PLAYER1)
                makeMachineMove(it.model!!, getNextGameState)
        }
    }

    override fun makeFirstMove(getNextGameState: (GameOverCheckResult) -> GameState) {
        if (model.isEmpty && model.player1GamePiece == GamePiece.O)
            makeMachineMove(model, getNextGameState)
    }

    override fun getCurrentPlayerPiece(): GamePiece = model.player1GamePiece  // because there will never be a player2 turn
}
