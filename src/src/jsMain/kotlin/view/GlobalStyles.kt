package view

import kotlinx.css.*
import rsuite.RSuiteSize
import view.components.GameBoardSquareStyles

// STYLE CONSTANTS

val defaultButtonSize = RSuiteSize.MD.value

const val marginMedium = "1.5rem"
const val marginLarge = "2.5rem"
const val marginHuge = "4rem"

const val tileBackgroundColor = "#131821"
const val tileBorderColor = "#27354d"
const val blueColor = "#087ad1" // the value of the "blue" react suite color
const val greenColor = "rgb(23, 135, 17)"
const val redColor = "rgb(210, 48, 12)"

val globalStyles = CssBuilder(allowClasses = false).apply {
    // Center the game

    body {
        display = Display.flex
        justifyContent = JustifyContent.center
    }

    // Round the board's corners

    val radius = LinearDimension("1rem")
    val cssClass = "${GameBoardSquareStyles.name}-${GameBoardSquareStyles::general.name}"

    "div:first-child > .$cssClass:first-child" {
        borderTopLeftRadius = radius
    }
    "div:first-child > .$cssClass:last-child" {
        borderTopRightRadius = radius
    }
    "div:last-child > .$cssClass:first-child" {
        borderBottomLeftRadius = radius
    }
    "div:last-child > .$cssClass:last-child" {
        borderBottomRightRadius = radius
    }

    // Give right margin to buttons that are next to each other

    ".rs-btn:not(:last-of-type)" {
        marginRight = LinearDimension(marginMedium)
    }
}
