package controller.move.factory

import controller.GameSettings
import controller.GameState
import controller.GameStateFactory
import controller.move.MoveStrategy
import model.GameModel
import react.StateSetter

abstract class MoveStrategyFactory {
    abstract fun createMoveStrategy(
        model: GameModel,
        settings: GameSettings,
        setGameState: StateSetter<GameState?>,
        setWaitingForServer: StateSetter<Boolean>,
        gameStateFactory: GameStateFactory
    ): MoveStrategy
}