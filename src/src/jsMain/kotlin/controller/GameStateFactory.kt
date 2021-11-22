package controller

import controller.move.MoveStrategy
import controller.states.*
import model.GameModel
import model.GameOverCheckResult
import react.StateSetter

class GameStateFactory(
    private val setGameState: StateSetter<GameState?>,
    private val setWaitingForServer: StateSetter<Boolean>
) {
    fun createWelcomeScreenState(): GameState
        = WelcomeScreenState(setGameState, setWaitingForServer, this)

    fun createPieceSelectionState(): GameState
        = PieceSelectionState(setGameState, setWaitingForServer, this)

    fun createSettingsState(): GameState
        = SettingsState(setGameState, setWaitingForServer, this)

    fun createPlayingState(model: GameModel, moveStrategy: MoveStrategy): GameState
        = PlayingState(model, setGameState, setWaitingForServer, this, moveStrategy)

    fun createPauseState(gameState: GameState): GameState
        = PauseState(gameState, setGameState, setWaitingForServer, this)

    fun createErrorState(errorMessage: String?): GameState
        = ErrorState(setGameState, setWaitingForServer, this, errorMessage)

    fun createGameOverState(model: GameModel, gameOverCheckResult: GameOverCheckResult): GameState
        = GameOverState(model, setGameState, setWaitingForServer, this, gameOverCheckResult)
}