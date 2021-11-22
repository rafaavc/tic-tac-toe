package ai

import ai.node.Node
import model.GameModel
import model.GameOverCheckResult
import model.GameOverType
import model.utilities.Position

class MiniMaxRobot : Robot {
    private val infinity = 10000

    override fun getNextPlay(model: GameModel): Position {
        val res = minimax(Node(model, GameOverCheckResult(GameOverType.NOT_OVER), true), -infinity, infinity, true)
        return res.first ?: error("Got null next play.")
    }

    private fun minimax(node: Node, alpha: Int, beta: Int, isMaximizer: Boolean, depth: Int = 0): Pair<Position?, Int> {
        if (node.isGameOver()) return Pair(node.move, node.getEval())

        var currentAlpha = alpha
        var currentBeta = beta

        node.expand()

        var bestRes: Pair<Position?, Int> = Pair(null, (if (isMaximizer) -1 else 1) * infinity)
        for (child in node.getChildren()) {
            val res = minimax(child, currentAlpha, currentBeta, !isMaximizer, depth + 1)
            if ((isMaximizer && res.second > bestRes.second) || (!isMaximizer && res.second < bestRes.second)) {
                bestRes = res
            }

            if (isMaximizer) currentAlpha = maxOf(res.second, alpha)
            else currentBeta = minOf(res.second, beta)

            if (currentBeta <= currentAlpha) break
        }

        return Pair(node.move ?: bestRes.first, bestRes.second)
    }
}