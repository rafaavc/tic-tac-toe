package controller.move

import controller.GameOverCheckResult
import controller.GameSettings
import controller.GameState
import model.GameModel
import model.GameOverType
import model.GamePiece
import model.GamePlayer
import model.utilities.Position
import react.StateSetter

class HvHStrategy(
    model: GameModel,
    settings: GameSettings,
    private val setGameState: StateSetter<GameState?>
) : MoveStrategy(model, settings) {

    private var currentPlayer = GamePlayer.PLAYER1

    private fun getGameOverMessage(gameOverCheckResult: GameOverCheckResult): Pair<String?, Boolean> {
        if (gameOverCheckResult.type == GameOverType.PLAYER1_VICTORY) return Pair("Player X wins!", true)
        if (gameOverCheckResult.type == GameOverType.PLAYER2_VICTORY) return Pair("Player O wins!", true)
        return Pair(null, false)
    }

    private fun fillGameOverCheckResult(gameOverCheckResult: GameOverCheckResult) {
        val gameOverMessage = getGameOverMessage(gameOverCheckResult)
        gameOverCheckResult.gameOverMessage = gameOverMessage.first
        gameOverCheckResult.showSuccess = gameOverMessage.second
    }

    override fun canMakeMove(squarePosition: Position) = model.isValidPlay(squarePosition)

    override fun makeMove(player: GamePlayer, squarePosition: Position,
                          getNextGameState: (GameOverCheckResult) -> GameState): GameState? {

        if (!model.makePlay(currentPlayer, squarePosition)) return null

        val gameOverCheckResult = controller.checkGameOver(currentPlayer, squarePosition, getWinningPieces = true)
        fillGameOverCheckResult(gameOverCheckResult)

        currentPlayer = if (currentPlayer == GamePlayer.PLAYER1) GamePlayer.PLAYER2 else GamePlayer.PLAYER1

        return getNextGameState(gameOverCheckResult).also {
            setGameState(it)
        }
    }

    override fun getCurrentPlayerPiece(): GamePiece
        = if (currentPlayer == GamePlayer.PLAYER1) model.player1GamePiece else model.player2GamePiece  // because there will never be a player2 turn
}
