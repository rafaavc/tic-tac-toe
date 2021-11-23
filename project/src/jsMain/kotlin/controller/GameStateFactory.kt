package controller

import controller.move.MoveStrategy
import controller.states.*
import model.GameModel
import react.StateSetter

class GameStateFactory(
    private val settings: GameSettings,
    private val setGameState: StateSetter<GameState?>,
    private val setWaitingForServer: StateSetter<Boolean>
) {
    fun createWelcomeScreenState(): GameState
        = WelcomeScreenState(setGameState, this, settings)

    fun createPlayingState(): GameState {
        val model = GameModel(settings.player1Piece, settings.boardSize, settings.target)
        val moveStrategy = settings.moveStrategyFactory.createMoveStrategy(model, settings, setGameState, setWaitingForServer, this)
        return PlayingState(model, setGameState, this, moveStrategy)
    }

    fun createPlayingState(model: GameModel, moveStrategy: MoveStrategy): GameState {
        return PlayingState(model, setGameState, this, moveStrategy)
    }

    fun createPauseState(gameState: GameState): GameState
            = PauseState(gameState, setGameState, this)

    fun createErrorState(errorMessage: String?): GameState
            = ErrorState(setGameState, this, errorMessage)

    fun createGameOverState(model: GameModel, gameOverCheckResult: GameOverCheckResult): GameState
            = GameOverState(model, setGameState, this, gameOverCheckResult)
}