package model

import model.utilities.Position

class GameOverCheckResult(val type: GameOverType, val winningPieces: Set<Position>? = null) {
    fun isOver(): Boolean = type != GameOverType.NOT_OVER
    companion object {
        fun getGameOverType(player: GamePlayer): GameOverType = when (player) {
                GamePlayer.MACHINE -> GameOverType.MACHINE_VICTORY
                GamePlayer.HUMAN -> GameOverType.HUMAN_VICTORY
            }
    }
}
