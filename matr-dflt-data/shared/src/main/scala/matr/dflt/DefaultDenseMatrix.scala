package matr.dflt

import matr.Matrix
import matr.util.RowMajorIndex

case class DefaultDenseMatrix[R <: Int, C <: Int, T](private val elements: Array[T])(
    using
    ValueOf[R],
    ValueOf[C],
    Matrix.Requirements.NonNegativeDimensions[R, C]
) extends Matrix[R, C, T]:
    lhs =>

    require(valueOf[R] * valueOf[C] == elements.size)

    override def apply(rowIdx: Int, colIdx: Int): T =
        Matrix.Requirements.positionWithinShape(rowIdx, colIdx, rowDim, colDim)
        elements(RowMajorIndex.toIdx(rowIdx, colIdx, colDim))

    override def toString(): String = s"DefaultDenseMatrix(${elements.mkString(",")})"
