package controller.move

import controller.GameState
import model.GameModel
import model.GameOverCheckResult
import model.GamePiece
import model.GamePlayer
import model.utilities.Position

abstract class MoveStrategy(protected val model: GameModel) {
    abstract fun canMakeMove(squarePosition: Position): Boolean
    abstract fun makeMove(player: GamePlayer, squarePosition: Position,
                          getNextGameState: (GameOverCheckResult) -> GameState): GameState?
    abstract fun makeFirstMove(getNextGameState: (GameOverCheckResult) -> GameState)
    abstract fun getCurrentPlayerPiece(): GamePiece
}
