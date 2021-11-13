import components.GameBoard
import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window
import react.dom.h1

fun main() {
    window.onload = {
        render(document.getElementById("root")) {
            h1 {
                +"TicTacToe"
            }
            child(GameBoard)
        }
    }
}
