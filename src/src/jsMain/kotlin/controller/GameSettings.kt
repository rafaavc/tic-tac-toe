package controller

import model.GamePiece

data class GameSettings(
    var player1Piece: GamePiece = GamePiece.O,
    var boardSize: Int = 10,
    var target: Int = 5,
    var timeToThink: Double = 3.0
) {
    fun set(player1Piece: GamePiece, boardSize: Int, target: Int, timeToThink: Double) {
        this.player1Piece = player1Piece
        this.boardSize = boardSize
        this.target = target
        this.timeToThink = timeToThink
    }
}
