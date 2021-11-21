package ai

import ai.node.MCTSNode
import model.GameModel
import model.GameOverCheckResult
import model.GameOverType
import model.GamePlayer
import model.utilities.Position

class MCTSRobot : Robot {
    override fun getNextPlay(gameModel: GameModel): Position {
        if (gameModel.isEmpty) return gameModel.getPossibleMoves(false).random()

        val root = MCTSNode(gameModel, GameOverCheckResult(GameOverType.NOT_OVER), true)
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
        val gameModel = node.gameModel.copy()
        var isMaximizer = node.isMaximizer
        var gameOverCheckResult = if (node.move != null) {
            gameModel.checkGameOver(getPlayerFromIsMaximizer(!isMaximizer /* we want the player from the last node */), node.move)
        } else GameOverCheckResult(GameOverType.NOT_OVER)

        val possibleMoves = gameModel.getPossibleMoves()

        while (!gameOverCheckResult.isOver()) {
            val move = possibleMoves.random()
            possibleMoves.remove(move)

            val player = getPlayerFromIsMaximizer(isMaximizer)

            gameModel.makePlay(player, move, false)

            gameOverCheckResult = gameModel.checkGameOver(player, move)
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