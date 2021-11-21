import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window
import react.dom.h1
import view.GameContainer
import rsuite.RSuiteCustomProvider

fun main() {
    window.onload = {
        render(document.getElementById("root")) {
            child(RSuiteCustomProvider) {
                attrs {
                    theme = "dark"
                }
                h1 {
                    +"TicTacToe"
                }
                child(GameContainer)
            }
        }
    }
}
