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

    override fun equals(other: Any?) = (other is Position) && other.x == x && other.y == y
    override fun hashCode() = 31 * x + y  // generated automatically
    override fun toString() = "($x, $y)"
}
