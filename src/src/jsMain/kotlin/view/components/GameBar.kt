package view.components

import kotlinx.css.*
import react.PropsWithChildren
import react.fc
import styled.css
import styled.styledDiv

external interface GameBarProps : PropsWithChildren {
    var minHeight: String?
    var marginBottom: String?
    var marginTop: String?
}

val GameBar = fc<GameBarProps> { props ->
    styledDiv {
        css {
            marginTop = LinearDimension(props.marginTop ?: "0")
            marginBottom = LinearDimension(props.marginBottom ?: "1.5rem")
            display = Display.flex
            justifyContent = JustifyContent.spaceBetween
            alignItems = Align.center
        }
        props.children()
    }
}
