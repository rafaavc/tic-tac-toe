import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window
import view.GameContainer
import rsuite.RSuiteCustomProvider
import styled.injectGlobal
import view.globalStyles

fun main() {
    window.onload = {
        injectGlobal(globalStyles)
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
