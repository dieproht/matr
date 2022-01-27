package matr.dflt

import matr.Matrix
import matr.MatrixFactory
import matr.Submatrix
import scala.compiletime.ops.boolean.*
import scala.compiletime.ops.int.*

trait DefaultSubmatrix:
   given defaultSubmatrix[RowIdxTL <: Int,
                          ColIdxTL <: Int,
                          RowIdxBR <: Int,
                          ColIdxBR <: Int,
                          R <: Int,
                          C <: Int,
                          T
   ]
            (using
             Submatrix.Requirements.WindowWithinShape[RowIdxTL, ColIdxTL, RowIdxBR, ColIdxBR, R, C],
             Matrix.Requirements.NonNegativeDimensions[
                RowIdxBR - RowIdxTL + 1,
                ColIdxBR - ColIdxTL + 1
             ]
            )
            (using
             ValueOf[RowIdxBR - RowIdxTL + 1],
             ValueOf[ColIdxBR - ColIdxTL + 1],
             ValueOf[RowIdxTL],
             ValueOf[ColIdxTL]
            )
            : Submatrix[RowIdxTL, ColIdxTL, RowIdxBR, ColIdxBR, R, C, T] with
      def submatrix
               (m: Matrix[R, C, T])
               : Matrix[RowIdxBR - RowIdxTL + 1, ColIdxBR - ColIdxTL + 1, T] = DefaultSubmatrix
         .SubmatrixView[RowIdxTL, ColIdxTL, RowIdxBR, ColIdxBR, R, C, T](m)

object DefaultSubmatrix extends DefaultSubmatrix:

   final class SubmatrixView[RowIdxTL <: Int,
                             ColIdxTL <: Int,
                             RowIdxBR <: Int,
                             ColIdxBR <: Int,
                             OrigR <: Int,
                             OrigC <: Int,
                             T
   ]
            (orig: Matrix[OrigR, OrigC, T])
            (using
             Matrix.Requirements.NonNegativeDimensions[
                RowIdxBR - RowIdxTL + 1,
                ColIdxBR - ColIdxTL + 1
             ]
            )
            (using
             ValueOf[RowIdxBR - RowIdxTL + 1],
             ValueOf[ColIdxBR - ColIdxTL + 1],
             ValueOf[RowIdxTL],
             ValueOf[ColIdxTL]
            )
       extends Matrix[RowIdxBR - RowIdxTL + 1, ColIdxBR - ColIdxTL + 1, T]:
      override def apply(rowIdx: Int, colIdx: Int): T =
         Matrix.Requirements.positionWithinShape(rowIdx, colIdx, rowDim, colDim)
         orig(valueOf[RowIdxTL] + rowIdx, valueOf[ColIdxTL] + colIdx)

      override def toString(): String = s"SubMatrixView(${orig.toString})"
