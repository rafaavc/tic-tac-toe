package ai.node

import model.GameModel
import model.GameOverCheckResult
import model.GameOverType
import model.utilities.Position
import kotlin.math.ln
import kotlin.math.sqrt

class MCTSNode(
    gameModel: GameModel,
    gameOverCheckResult: GameOverCheckResult,
    isMaximizer: Boolean,
    parent: MCTSNode? = null,
    move: Position? = null
) : Node(gameModel, gameOverCheckResult, isMaximizer, parent, move) {

    private var numberOfWins: Long = 0
    private var numberOfVisits: Long = 0

    fun incrementWins(gameOverType: GameOverType) {
        if (gameOverType == GameOverType.PLAYER2_VICTORY) numberOfWins++
        else if (gameOverType == GameOverType.PLAYER1_VICTORY) numberOfWins--
    }

    fun incrementNumberOfVisits() {
        numberOfVisits++
    }

    fun getScore() = numberOfWins

    override fun expand() {
        if (expanded) return

        val newModels = executePossibleMoves()
        for ((gameModel, gameOverCheckResult, move) in newModels) {
            nodeChildren.add(MCTSNode(gameModel, gameOverCheckResult, !isMaximizer, this, move))
        }

        expanded = true
    }

    fun findBestDescendantUCT(): MCTSNode {
        var node: MCTSNode = this
        while(!node.isGameOver() && node.isExpanded()) {
            if (node.getChildren().isEmpty()) {
                error("The node is not game over and is expanded, but its children list is empty!")
            }
            // this will never be null because we are checking that the node has children
            node = node.getChildren().maxByOrNull { (it as MCTSNode).calculateUCT() }!! as MCTSNode
        }
        return node
    }

    private fun calculateUCT(): Double {
        if (numberOfVisits == 0L) return Double.MAX_VALUE // ~infinity

        val left = numberOfWins.toDouble() / numberOfVisits.toDouble()
        val right = ln((parent as MCTSNode).numberOfVisits.toDouble()) / numberOfVisits.toDouble()

        return left + 1.4142 * sqrt(right)
    }

}