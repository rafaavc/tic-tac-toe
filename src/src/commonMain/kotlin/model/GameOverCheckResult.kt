package model

import model.utilities.Position

class GameOverCheckResult(val type: GameOverType, val winningPieces: Set<Position>? = null) {
    var gameOverMessage: String? = null
    var showSuccess: Boolean = false

    fun isOver(): Boolean = type != GameOverType.NOT_OVER

    companion object {
        fun getGameOverType(player: GamePlayer): GameOverType = when (player) {
                GamePlayer.PLAYER2 -> GameOverType.PLAYER2_VICTORY
                GamePlayer.PLAYER1 -> GameOverType.PLAYER1_VICTORY
            }
    }
}
