package model

import model.utilities.*
import kotlinx.serialization.Serializable

@Serializable
class GameModel(
    val gameBoard: Array<Array<GamePiece>>,
    val player1GamePiece: GamePiece,
    val boardSize: Int,
    val target: Int
) {
    private val player2GamePiece = if (player1GamePiece == GamePiece.X) GamePiece.O else GamePiece.X
    var lastPlay: Position? = null

    constructor(player1GamePiece: GamePiece, boardSize: Int = 10, target: Int = 5)
        : this(Array(boardSize) {
            Array(boardSize) { GamePiece.EMPTY }
        }, player1GamePiece, boardSize, target)

    private fun copyGameBoard(): Array<Array<GamePiece>> {
        return Array(boardSize) { y ->
            Array(boardSize) { x ->
                gameBoard[y][x]
            }
        }
    }

    fun isEmpty() = lastPlay == null

    fun copy(): GameModel = GameModel(copyGameBoard(), player1GamePiece, boardSize, target)

    fun isInsideBoard(position: Position) = position.x in 0 until boardSize && position.y in 0 until boardSize

    fun isValidPlay(position: Position) = isInsideBoard(position)
                                                    && gameBoard[position.y][position.x] == GamePiece.EMPTY

    fun getPlayerPiece(player: GamePlayer): GamePiece
        = if (player == GamePlayer.PLAYER1) player1GamePiece else player2GamePiece

    fun makePlay(player: GamePlayer, position: Position, checkForValidPlay: Boolean = true): Boolean {
        if (checkForValidPlay && !isValidPlay(position)) return false
        val gamePiece = getPlayerPiece(player)

        gameBoard[position.y][position.x] = gamePiece
        lastPlay = position

        return true
    }
}
