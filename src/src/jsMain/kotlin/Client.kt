import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.css.*
import view.GameContainer
import rsuite.RSuiteCustomProvider
import styled.css
import styled.injectGlobal
import styled.styledDiv
import styled.styledH1
import view.components.GameBoardSquareStyles

val styles = CssBuilder(allowClasses = false).apply {
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

fun main() {
    window.onload = {
        injectGlobal(styles)
        render(document.getElementById("root")) {
            child(RSuiteCustomProvider) {
                attrs {
                    theme = "dark"
                }
                child(GameContainer)
            }
        }
    }
}
