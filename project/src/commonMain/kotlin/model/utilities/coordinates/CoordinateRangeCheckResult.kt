package model.utilities.coordinates

enum class CoordinateRangeCheckResult(val value: Boolean) {
    FALSE_NO_GREATER(false), // doesn't contain the coordinate nor any that is larger than that one
    FALSE(false), // doesn't contain the coordinate but there may be larger ones that are contained
    TRUE(true)
}