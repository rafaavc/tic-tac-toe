package model

class GameOverCheckResult(val type: GameOverType) { // TODO add indexes of the pieces that make the 5-row
    fun isOver(): Boolean = type != GameOverType.NOT_OVER
    companion object {
        fun getGameOverType(player: GamePlayer): GameOverType = when (player) {
                GamePlayer.MACHINE -> GameOverType.MACHINE_VICTORY
                GamePlayer.HUMAN -> GameOverType.HUMAN_VICTORY
            }
    }
}
