package controller.move.factory

import controller.GameSettings
import controller.GameState
import controller.GameStateFactory
import controller.move.HvHStrategy
import controller.move.MoveStrategy
import model.GameModel
import react.StateSetter

class HvHStrategyFactory : MoveStrategyFactory() {
    override fun createMoveStrategy(
        model: GameModel,
        settings: GameSettings,
        setGameState: StateSetter<GameState?>,
        setWaitingForServer: StateSetter<Boolean>,
        gameStateFactory: GameStateFactory
    ): MoveStrategy
        = HvHStrategy(model, settings, setGameState)
}