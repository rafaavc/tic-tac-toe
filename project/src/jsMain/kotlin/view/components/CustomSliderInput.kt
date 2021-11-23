package view.components

import react.Props
import react.fc
import view.externals.RSuiteSlider
import view.marginLarge

external interface CustomSliderInputProps : Props {
    var label: String
    var value: Double
    var min: Double
    var max: Double
    var step: Double
    var onChange: (Double) -> Unit
}

val CustomSliderInput = fc<CustomSliderInputProps> { props ->
    child(Container) {
        attrs {
            flex = false
            marginBottom = marginLarge
        }

        child(LabelField) {
            attrs {
                label = props.label
            }

            child(RSuiteSlider) {
                attrs {
                    value = props.value
                    min = props.min
                    max = props.max
                    step = props.step
                    onChange = props.onChange
                }
            }
        }
    }
}
