package state

import getMachinePlay
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import model.GameModel
import model.GameOverCheckResult
import model.GamePiece
import model.GamePlayer
import model.utilities.Position
import react.StateSetter
import view.components.PlayingGame

private val scope = MainScope()

class PlayingGameState(gameModel: GameModel, setGameState: StateSetter<GameState?>,
        setWaitingForServer: StateSetter<Boolean>, isBeginning: Boolean = true)
            : GameState(gameModel, PlayingGame, setGameState, setWaitingForServer) {

    init {
        if (isBeginning && gameModel.humanGamePiece == GamePiece.O) {
            setWaitingForServer(true)
            scope.launch {
                clickSquare(GamePlayer.MACHINE, getMachinePlay(gameModel))
                setWaitingForServer(false)
            }
        }
    }

    override fun canClickSquare(squarePosition: Position): Boolean = gameModel!!.isValidPlay(squarePosition)

    private fun getNextGameState(gameOverCheckResult: GameOverCheckResult): GameState {
        if (gameOverCheckResult.isOver())
            return GameOverGameState(gameModel!!, setGameState, setWaitingForServer, gameOverCheckResult)

        return PlayingGameState(gameModel!!, setGameState, setWaitingForServer, false)
    }

    override fun getLastPlay(): Position? = gameModel!!.lastPlay

    override fun clickSquare(player: GamePlayer, squarePosition: Position): GameState? {
        if (!gameModel!!.makePlay(player, squarePosition)) return null

        val gameOverCheckResult = gameModel.checkGameOver(player, squarePosition, getWinningPieces = true)

        // maybe change this for a call to setGameState() hook?
        return getNextGameState(gameOverCheckResult).also {
            setGameState(it)

            if (!it.isGameOver() && player == GamePlayer.HUMAN) {
                setWaitingForServer(true)
                scope.launch {
                    clickSquare(GamePlayer.MACHINE, getMachinePlay(it.gameModel!!))
                    setWaitingForServer(false)
                }
            }
        }
    }

    override fun pause() {
        setGameState(PauseGameState(gameModel!!, setGameState, setWaitingForServer))
    }
}
