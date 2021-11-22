package matr.dflt

import matr.Matrix
import matr.util.RowMajorIndex
import scala.reflect.ClassTag

case class DefaultDenseMatrix[R <: Int, C <: Int, T]
         (private val elements: Array[T])
         (using Matrix.Requirements.NonNegativeDimensions[R, C])
         (using vr: ValueOf[R], vc: ValueOf[C])
    extends Matrix[R, C, T]:
   lhs =>

   require(vr.value * vc.value == elements.size)

   override def apply(rowIdx: Int, colIdx: Int): T =
      Matrix.Requirements.positionWithinShape(rowIdx, colIdx, rowDim, colDim)
      elements(RowMajorIndex.toIdx(rowIdx, colIdx, colDim))

   override def toString(): String = s"DefaultDenseMatrix(${elements.mkString(",")})"
