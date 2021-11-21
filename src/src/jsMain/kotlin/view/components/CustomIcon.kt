package view.components

import kotlinx.css.LinearDimension
import kotlinx.css.fontSize
import kotlinx.css.margin
import react.ComponentClass
import react.Props
import react.fc
import styled.css
import styled.styledDiv

val CustomIcon = { icon: ComponentClass<Props> ->
    fc<Props> {
        styledDiv {
            css {
                fontSize = LinearDimension("1.3em")
                margin = "-1px -1px -2px -1px"
            }
            child(icon)
        }
    }
}
