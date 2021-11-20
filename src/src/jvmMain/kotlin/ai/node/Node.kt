package ai.node

import model.*
import model.utilities.Position

class Node(private val gameModel: GameModel, private val gameOverCheckResult: GameOverCheckResult, val move: Position? = null) {
    private val children = mutableListOf<Node>()
    private val score = 10

    fun expand(isMaximizer: Boolean) {
        val possibleMoves = mutableListOf<Position>()
        for ((y, line) in gameModel.gameBoard.withIndex()) {
            for ((x, _) in line.withIndex()) {
                val position = Position(x, y)
                if (gameModel.isValidPlay(position)) possibleMoves.add(position)
            }
        }

        val player = if (isMaximizer) GamePlayer.MACHINE else GamePlayer.HUMAN

        for (move in possibleMoves) {
            val model = gameModel.copy()
            model.makePlay(player, move)
            children.add(Node(model, model.checkGameOver(player, move), move))
        }
    }

    fun isGameOver(): Boolean = gameOverCheckResult.isOver()

    fun getEval(): Int {
        return when(gameOverCheckResult.type) {
            GameOverType.DRAW -> 0
            GameOverType.NOT_OVER -> throw Exception("Trying to get eval without game being over!")
            GameOverType.HUMAN_VICTORY -> -score
            GameOverType.MACHINE_VICTORY -> score
        }
    }

    fun getChildren(): List<Node> {
        if (gameOverCheckResult.isOver()) {
            throw Exception("The game is over!")
        }
        if (children.size == 0) {
            throw Exception("The node hasn't been expanded!")
        }

        return children
    }
}
