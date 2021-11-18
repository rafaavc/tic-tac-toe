package state

import model.GameModel
import model.GameOverCheckResult
import model.GamePlayer
import model.utilities.Position
import react.StateSetter
import view.components.GameBoard

class PlayingGameState(gameModel: GameModel, setGameState: StateSetter<GameState?>) : GameState(gameModel, GameBoard, setGameState) {
    override fun canClickSquare(squarePosition: Position): Boolean = gameModel!!.isValidPlay(squarePosition)

    private fun getNextGameState(gameOverCheckResult: GameOverCheckResult): GameState {
        if (gameOverCheckResult.isOver()) return GameOverGameState(gameModel!!, setGameState, gameOverCheckResult.type)
        return PlayingGameState(gameModel!!, setGameState)
    }

    override fun clickSquare(player: GamePlayer, squarePosition: Position): GameState? {
        if (!gameModel!!.makePlay(player, squarePosition)) return null

        val gameOverCheckResult = gameModel.checkGameOver(player, squarePosition)

        // maybe change this for a call to setGameState() hook?
        return getNextGameState(gameOverCheckResult).also {
            setGameState(it)
        }
    }

    override fun pause() {
        setGameState(PauseGameState(gameModel!!, setGameState))
    }
}
