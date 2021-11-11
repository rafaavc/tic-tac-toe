import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.attrs
import react.dom.div
import react.dom.input

val Welcome = functionalComponent<RProps> {
    val (name, setName) = useState("world")

    div {
        +"Hello, $name!"
    }
    input {
        attrs {
            type = InputType.text
            value = name
            onChangeFunction = { event ->
                setName((event.target as HTMLInputElement).value)
            }
        }
    }
}
