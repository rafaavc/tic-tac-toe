package controller.move.factory

import controller.GameSettings
import controller.GameState
import controller.GameStateFactory
import controller.move.HvHStrategy
import controller.move.HvMStrategy
import controller.move.MoveStrategy
import model.GameModel
import react.StateSetter

class HvMStrategyFactory : MoveStrategyFactory() {
    override fun createMoveStrategy(
        model: GameModel,
        settings: GameSettings,
        setGameState: StateSetter<GameState?>,
        setWaitingForServer: StateSetter<Boolean>,
        gameStateFactory: GameStateFactory
    ): MoveStrategy
        = HvMStrategy(model, settings, setGameState, setWaitingForServer, gameStateFactory)
}