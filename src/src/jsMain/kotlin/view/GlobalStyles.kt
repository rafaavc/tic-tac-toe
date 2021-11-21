package view

import kotlinx.css.*
import view.components.GameBoardSquareStyles

val globalStyles = CssBuilder(allowClasses = false).apply {
    body {
        display = Display.flex
        justifyContent = JustifyContent.center
    }

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
}
