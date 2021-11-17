package model.utilities

import kotlinx.serialization.Serializable

@Serializable
open class Position(val x: Int, val y: Int) {
    operator fun plus(value: Position): Position {
        return Position(x + value.x, y + value.y)
    }

    operator fun times(value: Int): Position {
        return Position(x * value, y * value)
    }
}
