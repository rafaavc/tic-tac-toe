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

        val defenseCandidate = getDefenseCandidate(controller, model)  // the second element is the length of the line

        val root = mCTSLoop(model)
        val mCTSCandidate = root.getChildren().maxByOrNull { (it as MCTSNode).getScore() }!!.move!!

        if (defenseCandidate.first != null) {
            if (getLineSizeByMove(model, mCTSCandidate) > defenseCandidate.second) { // the MCTS suggested move makes my line bigger, so I can confidently do it
                return mCTSCandidate
            }
            return defenseCandidate.first!!
        }
        return mCTSCandidate
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

    private fun mCTSLoop(model: GameModel): MCTSNode {
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
        return root
    }

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

    private fun getDefenseCandidate(controller: GameController, model: GameModel): Pair<Position?, Int> {
        if (model.lastPlay == null) return Pair(null, 0)

        val lastPlay = model.lastPlay!!

        val (hasLine, count, _, emptyPositions) = controller.lineChecker.checkLineAtPosition(lastPlay, model.target - 1,
            getLinePieces = true, getEmptyPositionsAfterLineEnds = true)

        // if it has a line that is 1 away from winning, then we could defend
        if (hasLine && emptyPositions!!.size == 1) return Pair(emptyPositions.first().second, count)

        // if it has empty positions, check if that empty position connects two small lines that together would win the game
        else if (emptyPositions!!.isNotEmpty()) {
            for (positionTriple in emptyPositions) {
                val positionAfterEmpty = positionTriple.third ?: continue

                val gamePiece = model.gameBoard[lastPlay.y][lastPlay.x]

                // if there is a piece of the same type on the other side
                if (model.gameBoard[positionAfterEmpty.y][positionAfterEmpty.x] == gamePiece) {
                    val (_, countOfPiecesAfterEmpty) = controller.lineChecker.checkLineAtPosition(
                        positionAfterEmpty, model.target - 1, directionToCheck = positionTriple.first)

                    if (countOfPiecesAfterEmpty + count >= model.target - 1) return Pair(positionTriple.second, model.target - 1) // if the sum of the 2 halves is 1 away from winning
                }
            }
        }

        // if it has a line that is 2 away from winning and has two blank spaces on each side, then we may need to defend
        if (count == model.target - 2 && count >= 2 && emptyPositions.size == 2) {
            // if none of the pieces had another of the same type on the other side,
            // and there are two free squares (if there's only one it is not urgent)
            return Pair(emptyPositions.random().second, count)
        }

        return Pair(null, 0)
    }
}