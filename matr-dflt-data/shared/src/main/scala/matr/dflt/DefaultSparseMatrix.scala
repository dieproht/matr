package matr.dflt

import matr.Matrix

case class DefaultSparseMatrix[R <: Int, C <: Int, T](private val elements: Map[(Int, Int), T])(
    using
    ValueOf[R],
    ValueOf[C],
    Numeric[T],
    Matrix.Requirements.NonNegativeDimensions[R, C]
) extends Matrix[R, C, T]:
    lhs =>

    override def apply(rowIdx: Int, colIdx: Int): T =
        Matrix.Requirements.positionWithinShape(rowIdx, colIdx, rowDim, colDim)
        elements.getOrElse((rowIdx, colIdx), summon[Numeric[T]].zero)

    override def toString(): String = s"DefaultSparseMatrix(${elements.mkString(",")})"
