package controller

import controller.move.factory.HvMStrategyFactory
import controller.move.factory.MoveStrategyFactory
import model.GamePiece

data class GameSettings(
    var player1Piece: GamePiece = GamePiece.O,
    var boardSize: Int = 10,
    var target: Int = 5,
    var timeToThink: Double = 2.0,
    var moveStrategyFactory: MoveStrategyFactory = HvMStrategyFactory()
) {
    fun set(player1Piece: GamePiece, boardSize: Int, target: Int, timeToThink: Double, moveStrategyFactory: MoveStrategyFactory) {
        this.player1Piece = player1Piece
        this.boardSize = boardSize
        this.target = target
        this.timeToThink = timeToThink
        this.moveStrategyFactory = moveStrategyFactory
    }
}
