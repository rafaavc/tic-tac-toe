package ai

import ai.node.MCTSNode
import controller.GameController
import model.GameModel
import controller.GameOverCheckResult
import controller.LineChecker
import model.GameOverType
import model.GamePlayer
import model.utilities.Position
import model.utilities.coordinates.InnerCoordinateRange

class MCTSRobot(private val time: Int) : Robot {
    override fun getNextPlay(model: GameModel): Position {
        val controller = GameController(model)
        if (model.isEmpty()) {
            val border = maxOf(model.boardSize / 4, 1)
            val range = InnerCoordinateRange(border, model.boardSize - 1 - border, border, model.boardSize - 1 - border)
            return controller.getPossibleMoves(range).random()
        }

        var defenseCandidate: Position? = null
        var lineToDefendSize = 0

        if (model.lastPlay != null) {
            val (hasLine, count, _, emptyPositions) = controller.lineChecker.checkLineAtPosition(model.lastPlay!!, model.target - 1,
                getLinePieces = true, getEmptyPositionsAfterLineEnds = true)

            lineToDefendSize = count

            // if it has a line that is 1 away from winning, then we could defend
            if (hasLine && emptyPositions!!.size == 1) defenseCandidate = emptyPositions.first()
            // if it has a line that is 2 away from winning and has two blank spaces on each side, then we may need to defend
            else if (count == model.target - 2 && count >= 2 && emptyPositions!!.size == 2) defenseCandidate = emptyPositions.random()
        }

        val root = MCTSNode(model, GameOverCheckResult(GameOverType.NOT_OVER), true)
        root.expand()

        var count = 0
        val start = System.currentTimeMillis()
        while(System.currentTimeMillis() - start < time) {
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

        val mctsCandidate = root.getChildren().maxByOrNull { (it as MCTSNode).getScore() }!!.move!!

        if (defenseCandidate != null) {
            if (getLineSizeByMove(model, mctsCandidate) > lineToDefendSize) { // the MCTS suggested move makes my line bigger, so I can confidently do it
                println("Using MCTS candidate even though had defense candidate")
                return mctsCandidate
            }

            println("Using defense candidate")
            return defenseCandidate
        }
        println("Using MCTS candidate")
        return mctsCandidate
    }

    private fun getLineSizeByMove(gameModel: GameModel, move: Position): Int {
        // assumes that the model that is passed is not game over
        val player = getPlayerFromIsMaximizer(true)
        val model = gameModel.copy()
        model.makePlay(player, move, false)

        val (_, count) = LineChecker(model).checkLineAtPosition(move, model.target)
        return count
    }

    private fun getPlayerFromIsMaximizer(isMaximizer: Boolean): GamePlayer
        = if (isMaximizer) GamePlayer.PLAYER2 else GamePlayer.PLAYER1

    private fun simulateRandomPlayout(node: MCTSNode): GameOverCheckResult {
        val model = node.model.copy()
        val controller = GameController(model)
        var isMaximizer = node.isMaximizer

        var gameOverCheckResult = if (node.move != null) {
            controller.checkGameOver(getPlayerFromIsMaximizer(!isMaximizer /* we want the player from the last node */), node.move, false)
        } else GameOverCheckResult(GameOverType.NOT_OVER)

        val possibleMoves = controller.getPossibleMoves()

        while (!gameOverCheckResult.isOver()) {
            val move = possibleMoves.random()
            possibleMoves.remove(move)

            val player = getPlayerFromIsMaximizer(isMaximizer)

            model.makePlay(player, move, false)

            gameOverCheckResult = controller.checkGameOver(player, move, false)
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