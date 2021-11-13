package components

import csstype.FontFamily
import game.GamePiece
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.*
import react.dom.attrs
import styled.css
import styled.styledDiv

external interface GameBoardSquareProps : Props {
    var piece: GamePiece
    var onClickFunction: (Event) -> Unit
    var canClick: Boolean
}

val GameBoardSquare = fc<GameBoardSquareProps> { props ->
    styledDiv {
        +"${if (props.piece == GamePiece.EMPTY) '_' else props.piece}"

        css {
            display = Display.inlineBlock
            fontFamily = FontFamily.monospace.toString()
            fontSize = LinearDimension("2rem")
            marginRight = LinearDimension("1rem")
            cursor = Cursor.pointer
            userSelect = UserSelect.none
        }
        if (props.canClick) {
            attrs {
                onClickFunction = props.onClickFunction
            }
        }
    }
}