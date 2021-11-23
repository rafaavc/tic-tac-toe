package view.components

import kotlinx.css.LinearDimension
import kotlinx.css.marginBottom
import react.PropsWithChildren
import react.fc
import styled.css
import styled.styledP

external interface LabelFieldProps : PropsWithChildren {
    var label: String
}

val LabelField = fc<LabelFieldProps> { props->
    styledP {
        css {
            marginBottom = LinearDimension(".5rem")
        }

        +props.label
    }
    props.children()
}
