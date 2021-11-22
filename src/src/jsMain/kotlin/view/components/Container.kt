package view.components

import kotlinx.css.*
import react.PropsWithChildren
import react.fc
import styled.css
import styled.styledDiv
import view.marginMedium

external interface ContainerProps : PropsWithChildren {
    var minHeight: String?
    var marginBottom: String?
    var marginTop: String?
    var flex: Boolean?
}

val Container = fc<ContainerProps> { props ->
    styledDiv {
        css {
            marginTop = LinearDimension(props.marginTop ?: "0")
            marginBottom = LinearDimension(props.marginBottom ?: marginMedium)

            if (props.flex != false) {
                display = Display.flex
                justifyContent = JustifyContent.spaceBetween
                alignItems = Align.center
            }
        }
        props.children()
    }
}
