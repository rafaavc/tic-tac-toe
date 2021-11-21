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
    var size: RSuiteSize
    var onClick: (Event) -> Unit
    var disabled: Boolean
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
