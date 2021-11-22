package controller.move

import controller.GameState
import controller.GameStateFactory
import getMachinePlay
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.*
import model.utilities.Position
import react.StateSetter

private val scope = MainScope()

class HvMStrategy(
    model: GameModel,
    private val setGameState: StateSetter<GameState?>,
    private val setWaitingForServer: StateSetter<Boolean>,
    private val gameStateFactory: GameStateFactory
) : MoveStrategy(model) {

    private fun getGameOverMessage(gameOverCheckResult: GameOverCheckResult): Pair<String?, Boolean> {
        if (gameOverCheckResult.type == GameOverType.PLAYER1_VICTORY) return Pair("You won :)", true)
        if (gameOverCheckResult.type == GameOverType.PLAYER2_VICTORY) return Pair("You lost :(", false)
        return Pair(null, false)
    }

    private fun fillGameOverCheckResult(gameOverCheckResult: GameOverCheckResult) {
        val gameOverMessage = getGameOverMessage(gameOverCheckResult)
        gameOverCheckResult.gameOverMessage = gameOverMessage.first
        gameOverCheckResult.showSuccess = gameOverMessage.second
    }

    private fun makeMachineMove(model: GameModel, getNextGameState: (GameOverCheckResult) -> GameState) {
        setWaitingForServer(true)
        scope.launch {
            try {
                if (model.isEmpty) delay(500)
                makeMove(GamePlayer.PLAYER2, getMachinePlay(model), getNextGameState)
            } catch (e: Throwable) {
                setGameState(gameStateFactory.createErrorState(e.message))
            }
            setWaitingForServer(false)
        }
    }

    override fun canMakeMove(squarePosition: Position) = model.isValidPlay(squarePosition)

    override fun makeMove(player: GamePlayer, squarePosition: Position,
                          getNextGameState: (GameOverCheckResult) -> GameState): GameState? {

        if (!model.makePlay(player, squarePosition)) return null

        val gameOverCheckResult = controller.checkGameOver(player, squarePosition, getWinningPieces = true)
        fillGameOverCheckResult(gameOverCheckResult)

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
