package model.utilities

class PositionDelta(xDelta: Int, yDelta: Int): Position(xDelta, yDelta) {
    init {
        if (xDelta > 1 || xDelta < -1 || yDelta < -1 || yDelta > 1)
            throw Exception("Invalid model.utilities.PositionDelta($xDelta, $yDelta)")
    }
}
