package matr.impl

import matr.Matrix

import scala.collection.mutable

private[matr] object MkString:

    inline def apply[R <: Int, C <: Int, T](m: Matrix[R, C, T], elemToString: T => String): String =
        val (colWidths: Array[Int], rowMajorStrings: Array[Array[String]]) = preprocessMatrix(m, elemToString)
        renderMatrix(m.rowDim, m.colDim, colWidths, rowMajorStrings)

    private inline def preprocessMatrix[R <: Int, C <: Int, T](m: Matrix[R, C, T], elemToString: T => String)
        : (Array[Int], Array[Array[String]]) =
        val colWidths: Array[Int] = new Array(m.colDim)
        val rowMajorStrings: Array[Array[String]] = Array.ofDim(m.rowDim, m.colDim)
        var c: Int = 0
        while c < m.colDim do
            var colWidth: Int = 0
            var r: Int = 0
            while r < m.rowDim do
                val elemStr = elemToString(m(r, c))
                colWidth = math.max(colWidth, elemStr.size)
                rowMajorStrings(r)(c) = elemStr
                r = r + 1
            colWidths(c) = colWidth
            c = c + 1
        (colWidths, rowMajorStrings)

    private inline def renderMatrix(
        rowDim: Int,
        colDim: Int,
        colWidths: Array[Int],
        rowMajorStrings: Array[Array[String]]
    ): String =
        val builder = new StringBuilder
        if rowDim == 1 then
            renderSingleRowMatrix(builder, colDim, rowMajorStrings)
        else
            renderMultiRowMatrix(builder, rowDim, colDim, colWidths, rowMajorStrings)
        builder.result

    private inline def renderSingleRowMatrix(builder: StringBuilder, colDim: Int, rowMajorStrings: Array[Array[String]])
        : Unit =
        builder += '('
        var colIdx: Int = 0
        while colIdx < colDim do
            builder ++= rowMajorStrings(0)(colIdx)
            if colIdx < colDim - 1 then
                builder ++= ", "
            colIdx = colIdx + 1
        builder += ')'

    private inline def renderMultiRowMatrix(
        builder: StringBuilder,
        rowDim: Int,
        colDim: Int,
        colWidths: Array[Int],
        rowMajorStrings: Array[Array[String]]
    )
        : Unit =
        builder += PAREN_TOP_LEFT
        var rowIdx: Int = 0
        while rowIdx < rowDim do
            if rowIdx > 0 && rowIdx < rowDim - 1 then
                builder += PAREN_MID_LEFT
            if rowIdx == rowDim - 1 then
                builder += PAREN_BTM_LEFT
            var colIdx: Int = 0
            while colIdx < colDim do
                builder ++= " " * (colWidths(colIdx) - rowMajorStrings(rowIdx)(colIdx).size)
                builder ++= rowMajorStrings(rowIdx)(colIdx)
                if colIdx < colDim - 1 then
                    builder ++= ", "
                colIdx = colIdx + 1
            if rowIdx == 0 then
                builder += PAREN_TOP_RIGHT
                builder += NEW_LINE
            if rowIdx > 0 && rowIdx < rowDim - 1 then
                builder += PAREN_MID_RIGHT
                builder += NEW_LINE
            rowIdx = rowIdx + 1
        builder += PAREN_BTM_RIGHT

    private val NEW_LINE: Char = '\n'
    private val PAREN_TOP_LEFT: Char = '\u239B'
    private val PAREN_MID_LEFT: Char = '\u239C'
    private val PAREN_BTM_LEFT: Char = '\u239D'
    private val PAREN_TOP_RIGHT: Char = '\u239E'
    private val PAREN_MID_RIGHT: Char = '\u239F'
    private val PAREN_BTM_RIGHT: Char = '\u23A0'
