package ai

import ai.node.MCTSNode
import controller.GameController
import model.GameModel
import model.GameOverCheckResult
import model.GameOverType
import model.GamePlayer
import model.utilities.Position

class MCTSRobot : Robot {
    override fun getNextPlay(model: GameModel): Position {
        // TODO return only moves from a smaller scope in the center of the board
        if (model.isEmpty) return GameController(model).getPossibleMoves(false).random()

        val root = MCTSNode(model, GameOverCheckResult(GameOverType.NOT_OVER), true)
        root.expand()

        var count = 0
        val start = System.currentTimeMillis()
        while(System.currentTimeMillis() - start < 3000) {
            val promisingNode = root.findBestDescendantUCT()
            if (!promisingNode.isGameOver())
                promisingNode.expand()

            var explorationNode = promisingNode
            if (!promisingNode.isGameOver()) {
                if (promisingNode.getChildren().isEmpty())
                    error("Node is not game over but its children list is empty!")
                explorationNode = promisingNode.getChildren().random() as MCTSNode
            }

            val gameOverCheckResult = simulateRandomPlayout(explorationNode)
            backpropagation(explorationNode, gameOverCheckResult)
            count++
        }

        println("$count iterations")

        return root.getChildren().maxByOrNull { (it as MCTSNode).getScore() }!!.move!!
    }

    private fun getPlayerFromIsMaximizer(isMaximizer: Boolean): GamePlayer
        = if (isMaximizer) GamePlayer.PLAYER2 else GamePlayer.PLAYER1

    private fun simulateRandomPlayout(node: MCTSNode): GameOverCheckResult {
        val model = node.model.copy()
        val controller = GameController(model)
        var isMaximizer = node.isMaximizer

        var gameOverCheckResult = if (node.move != null) {
            controller.checkGameOver(getPlayerFromIsMaximizer(!isMaximizer /* we want the player from the last node */), node.move)
        } else GameOverCheckResult(GameOverType.NOT_OVER)

        val possibleMoves = controller.getPossibleMoves()

        while (!gameOverCheckResult.isOver()) {
            val move = possibleMoves.random()
            possibleMoves.remove(move)

            val player = getPlayerFromIsMaximizer(isMaximizer)

            model.makePlay(player, move, false)

            gameOverCheckResult = controller.checkGameOver(player, move)
            isMaximizer = !isMaximizer
        }

        return gameOverCheckResult
    }

    private fun backpropagation(node: MCTSNode, gameOverCheckResult: GameOverCheckResult) {
        var currentNode: MCTSNode? = node
        while(currentNode != null) {
            currentNode.incrementWins(gameOverCheckResult.type)
            currentNode.incrementNumberOfVisits()
            currentNode = currentNode.parent as MCTSNode?
        }
    }
}