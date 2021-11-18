import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window
import react.dom.h1
import view.GameContainer

fun main() {
    window.onload = {
        render(document.getElementById("root")) {
            h1 {
                +"TicTacToe"
            }
            child(GameContainer)
        }
    }
}
