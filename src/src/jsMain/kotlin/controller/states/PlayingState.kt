package controller.states

import controller.GameState
import getMachinePlay
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import model.GameModel
import model.GameOverCheckResult
import model.GamePiece
import model.GamePlayer
import model.utilities.Position
import react.StateSetter
import view.states.PlayingGame

private val scope = MainScope()

class PlayingGameState(
    model: GameModel,
    setGameState: StateSetter<GameState?>,
    setWaitingForServer: StateSetter<Boolean>,
    isBeginning: Boolean = true
) : GameState(model, PlayingGame, setGameState, setWaitingForServer) {

    init {
        if (isBeginning && model.humanGamePiece == GamePiece.O) {
            setWaitingForServer(true)
            scope.launch {
                clickSquare(GamePlayer.MACHINE, getMachinePlay(model))
                setWaitingForServer(false)
            }
        }
    }

    override fun canClickSquare(squarePosition: Position): Boolean = model!!.isValidPlay(squarePosition)

    private fun getNextGameState(gameOverCheckResult: GameOverCheckResult): GameState {
        if (gameOverCheckResult.isOver())
            return GameOverState(model!!, setGameState, setWaitingForServer, gameOverCheckResult)

        return PlayingGameState(model!!, setGameState, setWaitingForServer, false)
    }

    override fun getLastPlay(): Position? = model!!.lastPlay

    override fun clickSquare(player: GamePlayer, squarePosition: Position): GameState? {
        if (!model!!.makePlay(player, squarePosition)) return null

        val gameOverCheckResult = model.checkGameOver(player, squarePosition, getWinningPieces = true)

        // maybe change this for a call to setGameState() hook?
        return getNextGameState(gameOverCheckResult).also {
            setGameState(it)

            if (!it.isGameOver() && player == GamePlayer.HUMAN) {
                setWaitingForServer(true)
                scope.launch {
                    clickSquare(GamePlayer.MACHINE, getMachinePlay(it.model!!))
                    setWaitingForServer(false)
                }
            }
        }
    }

    override fun pause() {
        setGameState(PauseState(model!!, setGameState, setWaitingForServer))
    }
}
