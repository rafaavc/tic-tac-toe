package ai.node

import controller.GameController
import controller.GameOverCheckResult
import model.*
import model.utilities.Position

open class Node(
    val model: GameModel,
    private val gameOverCheckResult: GameOverCheckResult,
    val isMaximizer: Boolean,
    val parent: Node? = null,
    val move: Position? = null
) {
    private val score = 10
    private val controller = GameController(model)
    protected val nodeChildren = mutableListOf<Node>()
    protected var expanded = false

    protected fun executePossibleMoves(): List<Triple<GameModel, GameOverCheckResult, Position>> {
        val player = if (isMaximizer) GamePlayer.PLAYER2 else GamePlayer.PLAYER1
        val possibleMoves = controller.getPossibleMoves(true)
        val newModels = mutableListOf<Triple<GameModel, GameOverCheckResult, Position>>()

        for (move in possibleMoves) {
            val model = model.copy()
            model.makePlay(player, move)
            newModels.add(Triple(model, GameController(model).checkGameOver(player, move, false), move))
        }

        return newModels
    }

    open fun expand() {
        if (expanded) return

        val newModels = executePossibleMoves()
        for ((gameModel, gameOverCheckResult, move) in newModels) {
            nodeChildren.add(Node(gameModel, gameOverCheckResult, !isMaximizer, this, move))
        }

        expanded = true
    }

    fun isGameOver(): Boolean = gameOverCheckResult.isOver()

    fun isExpanded(): Boolean = expanded

    fun getEval(): Int {
        return when(gameOverCheckResult.type) {
            GameOverType.DRAW -> 0
            GameOverType.NOT_OVER -> throw Exception("Trying to get eval without game being over!")
            GameOverType.PLAYER1_VICTORY -> -score
            GameOverType.PLAYER2_VICTORY -> score
        }
    }

    fun getChildren(): List<Node> {
        if (gameOverCheckResult.isOver()) {
            throw Exception("The game is over!")
        }
        if (nodeChildren.size == 0) {
            throw Exception("The node hasn't been expanded!")
        }

        return nodeChildren
    }
}
