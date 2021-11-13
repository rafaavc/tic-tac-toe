package ai

import game.GameState
import utilities.Position

interface Robot {
    fun getNextPlay(gameState: GameState): Position
}