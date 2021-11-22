package view.components

import csstype.FontFamily
import model.GamePiece
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.*
import react.dom.attrs
import react.dom.onMouseOut
import react.dom.onMouseOver
import styled.StyleSheet
import styled.css
import styled.styledDiv
import styled.styledSpan
import view.*

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

        border = "1px solid $tileBorderColor"
        backgroundColor = Color(tileBackgroundColor)
    }
}

private fun getPieceColor(won: Boolean, showSuccess: Boolean, isLastPlay: Boolean, pieceRepresentation: String?): Color {
    if (pieceRepresentation != null) return Color(tileBorderColor)
    if (won) return if (showSuccess) Color(greenColor) else Color(redColor)
    if (isLastPlay) return Color(blueColor)
    return Color.inherit
}

external interface GameBoardSquareProps : Props {
    var piece: GamePiece
    var onClickFunction: (Event) -> Unit
    var canClick: Boolean
    var won: Boolean
    var showSuccess: Boolean?
    var lastPlay: Boolean
    var currentPlayerPiece: GamePiece
}

val GameBoardSquare = fc<GameBoardSquareProps> { props ->
    var pieceRepresentation by useState<String?>(null)

    // when props.piece is updated the pieceRepresentation is reset
    useEffect(props.piece) {
        pieceRepresentation = null
    }

    styledDiv {
        styledSpan {
            css {
                color = getPieceColor(props.won, props.showSuccess == true, props.lastPlay, pieceRepresentation)
                opacity = if (pieceRepresentation != null) 0.7 else 1
            }
            +(pieceRepresentation ?: props.piece.value)
        }

        css {
            cursor = if (props.canClick) Cursor.pointer else Cursor.auto
            +GameBoardSquareStyles.general
        }

        if (props.canClick)
            attrs {
                onClickFunction = props.onClickFunction
                onMouseOver = { pieceRepresentation = props.currentPlayerPiece.value }
                onMouseOut = { pieceRepresentation = null }
            }
    }
}
