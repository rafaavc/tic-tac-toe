package state

import model.GameModel
import model.GameOverCheckResult
import model.GameOverType
import model.GamePlayer
import model.utilities.Position

class PlayingGameState(gameModel: GameModel) : GameState(gameModel) {
    override fun canClickSquare(squarePosition: Position): Boolean = gameModel.isValidPlay(squarePosition)

    private fun getNextGameState(gameOverCheckResult: GameOverCheckResult): GameState {
        if (gameOverCheckResult.isOver()) return GameOverGameState(gameModel, gameOverCheckResult.type)
        return PlayingGameState(gameModel)
    }

    override fun clickSquare(player: GamePlayer, squarePosition: Position): GameState? {
        if (!gameModel.makePlay(player, squarePosition)) return null

        val gameOverCheckResult = gameModel.checkGameOver(player, squarePosition)

        // maybe change this for a call to setGameState() hook?
        return getNextGameState(gameOverCheckResult)
    }

    override fun isGameOver(): Boolean = false
    override fun getGameOverType(): GameOverType = GameOverType.NOT_OVER
}