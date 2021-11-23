package controller.move

import controller.GameController
import controller.GameState
import model.GameModel
import controller.GameOverCheckResult
import controller.GameSettings
import model.GamePiece
import model.GamePlayer
import model.utilities.Position

abstract class MoveStrategy(protected val model: GameModel, protected val settings: GameSettings) {
    protected val controller = GameController(model)

    abstract fun canMakeMove(squarePosition: Position): Boolean
    abstract fun makeMove(player: GamePlayer, squarePosition: Position,
                          getNextGameState: (GameOverCheckResult) -> GameState): GameState?
    open fun makeFirstMove(getNextGameState: (GameOverCheckResult) -> GameState) {}
    abstract fun getCurrentPlayerPiece(): GamePiece
}
