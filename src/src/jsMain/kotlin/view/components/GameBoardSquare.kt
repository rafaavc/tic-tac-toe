package view.components

import csstype.FontFamily
import model.GamePiece
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.*
import react.dom.attrs
import styled.StyleSheet
import styled.css
import styled.styledDiv

object GameBoardSquareStyles : StyleSheet("GameBoardSquareStyles", isStatic = true) {
    val general by css {
        display = Display.inlineBlock
        textAlign = TextAlign.center
        verticalAlign = VerticalAlign.middle

        width = LinearDimension("2.7rem")
        height = LinearDimension("2.7rem")

        userSelect = UserSelect.none

        fontFamily = FontFamily.monospace.toString()
        fontSize = LinearDimension("2rem")

        border = "1px solid #27354d"
        backgroundColor = Color("#131821")
    }
}

external interface GameBoardSquareProps : Props {
    var piece: GamePiece
    var onClickFunction: (Event) -> Unit
    var canClick: Boolean
    var won: Boolean
    var lastPlay: Boolean
}

val GameBoardSquare = fc<GameBoardSquareProps> { props ->
    styledDiv {
        +"${if (props.piece == GamePiece.EMPTY) ' ' else props.piece}"

        css {
            cursor = if (props.canClick) Cursor.pointer else Cursor.auto
            color = if (props.won) Color.red else {
                if (props.lastPlay) Color.green else Color.inherit
            }
            +GameBoardSquareStyles.general
        }
        if (props.canClick) {
            attrs {
                onClickFunction = props.onClickFunction
            }
        }
    }
}
