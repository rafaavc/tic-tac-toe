package controller

import model.GameModel
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
    protected val gameStateFactory: GameStateFactory
) {

    private fun nothingToDo() {
        println("Nothing to do here!")
    }

    fun quit() {
        setGameState(gameStateFactory.createWelcomeScreenState())
    }

    open fun getCurrentPlayerPiece(): GamePiece = GamePiece.EMPTY
    open fun canMakeMove(squarePosition: Position): Boolean = false
    open fun makeMove(player: GamePlayer, squarePosition: Position): GameState? = null
    open fun play() { nothingToDo() }
    open fun pause() { nothingToDo() }
    open fun isGameOver(): Boolean = false
}
