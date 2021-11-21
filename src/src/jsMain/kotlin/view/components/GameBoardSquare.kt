package view.components

import csstype.FontFamily
import model.GamePiece
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
    var won: Boolean
    var lastPlay: Boolean
}

val GameBoardSquare = fc<GameBoardSquareProps> { props ->
    styledDiv {
        +"${if (props.piece == GamePiece.EMPTY) '_' else props.piece}"

        css {
            display = Display.inlineBlock
            fontFamily = FontFamily.monospace.toString()
            fontSize = LinearDimension("2rem")
            marginRight = LinearDimension("1rem")
            cursor = if (props.canClick) Cursor.pointer else Cursor.auto
            userSelect = UserSelect.none
            color = if (props.won) Color.red else {
                if (props.lastPlay) Color.green else Color.inherit
            }
        }
        if (props.canClick) {
            attrs {
                onClickFunction = props.onClickFunction
            }
        }
    }
}
