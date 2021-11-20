package controller

import controller.states.WelcomeScreenState
import model.GameModel
import model.GameOverType
import model.GamePiece
import model.GamePlayer
import model.utilities.Position
import react.FC
import react.StateSetter
import view.ViewProps

abstract class GameState(
    val model: GameModel?,
    val gameView: FC<ViewProps>,
    protected val setGameState: StateSetter<GameState?>,
    protected val setWaitingForServer: StateSetter<Boolean>
) {

    private fun nothingToDo() {
        println("Nothing to do here!")
    }

    fun quit() {
        setGameState(WelcomeScreenState(setGameState, setWaitingForServer))
    }

    open fun canClickSquare(squarePosition: Position): Boolean = false
    open fun clickSquare(player: GamePlayer, squarePosition: Position): GameState? = null
    open fun isGameOver(): Boolean = false
    open fun getGameOverType(): GameOverType = GameOverType.NOT_OVER
    open fun getWinningPieces(): Set<Position>? = null
    open fun getLastPlay(): Position? = null
    open fun choosePlayer(piece: GamePiece) { nothingToDo() }
    open fun play() { nothingToDo() }
    open fun pause() { nothingToDo() }
}
