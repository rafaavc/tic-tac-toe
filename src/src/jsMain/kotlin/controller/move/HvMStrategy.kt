package controller.move

import controller.GameState
import getMachinePlay
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.GameModel
import model.GameOverCheckResult
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
            if (model.isEmpty) delay(500)
            makeMove(GamePlayer.PLAYER2, getMachinePlay(model), getNextGameState)
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

    override fun makeFirstMove(getNextGameState: (GameOverCheckResult) -> GameState)
        = makeMachineMove(model, getNextGameState)
}
