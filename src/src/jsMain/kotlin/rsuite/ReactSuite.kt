@file:JsModule("rsuite")
@file:JsNonModule

package rsuite

import org.w3c.dom.events.Event
import react.*

@JsName("Button")
external val RSuiteButton: ComponentClass<RSuiteButtonProps>

@JsName("IconButton")
external val RSuiteIconButton: ComponentClass<RSuiteIconButtonProps>

external interface RSuiteButtonProps : Props {
    var size: String
    var onClick: (Event) -> Unit
    var disabled: Boolean
    var active: Boolean
    var color: String
    var appearance: String
}

external interface RSuiteIconButtonProps : RSuiteButtonProps {
    var icon: ReactNode
    var circle: Boolean
}

@JsName("CustomProvider")
external val RSuiteCustomProvider: ComponentClass<RSuiteCustomProviderProps>

external interface RSuiteCustomProviderProps : Props {
    var theme: String
}

@JsName("Loader")
external val RSuiteLoader: ComponentClass<RSuiteLoaderProps>

external interface RSuiteLoaderProps : Props {
    var content: Any
}

@JsName("Slider")
external val RSuiteSlider: ComponentClass<RSuiteSliderProps>

external interface RSuiteSliderProps : Props {
    var value: Double
    var min: Double
    var step: Double
    var max: Double
    var onChange: (value: Double) -> Unit
}
