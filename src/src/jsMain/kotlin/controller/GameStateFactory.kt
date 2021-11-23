package controller

import controller.move.HvMStrategy
import controller.states.*
import model.GameModel
import react.StateSetter

class GameStateFactory(
    private val settings: GameSettings,
    private val setGameState: StateSetter<GameState?>,
    private val setWaitingForServer: StateSetter<Boolean>
) {
    fun createWelcomeScreenState(): GameState
        = WelcomeScreenState(setGameState, setWaitingForServer, this, settings)

    fun createPlayingState(): GameState {
        val model = GameModel(settings.player1Piece, settings.boardSize, settings.target)
        // TODO move moveStrategy to settings and implement abstract factory
        val moveStrategy = HvMStrategy(model, settings, setGameState, setWaitingForServer, this)
        return PlayingState(model, setGameState, setWaitingForServer, this, moveStrategy)
    }

    fun createPauseState(gameState: GameState): GameState
            = PauseState(gameState, setGameState, setWaitingForServer, this)

    fun createErrorState(errorMessage: String?): GameState
            = ErrorState(setGameState, setWaitingForServer, this, errorMessage)

    fun createGameOverState(model: GameModel, gameOverCheckResult: GameOverCheckResult): GameState
            = GameOverState(model, setGameState, setWaitingForServer, this, gameOverCheckResult)
}