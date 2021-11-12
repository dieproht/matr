package matr

import scala.compiletime.ops.boolean.&&
import scala.compiletime.ops.int.{<, >=, +, -}

/** Type class for getting the submatrix of a Matrix.
  *
  * @tparam RowIdxTL
  *   Row index of the element that should become the top-left element of the submatrix
  * @tparam ColIdxTL
  *   Column index of the element that should become the top-left element of the submatrix
  * @tparam RowIdxBR
  *   Row index of the element that should become the bottom-right element of the submatrix
  * @tparam ColIdxBR
  *   Column index of the element that should become the bottom-right element of the submatrix
  * @tparam R
  *   Row dimension of the original Matrix
  * @tparam C
  *   Column dimension of the original Matrix
  * @tparam T
  *   Element type of the Matrix
  */
trait Submatrix[RowIdxTL <: Int,
                ColIdxTL <: Int,
                RowIdxBR <: Int,
                ColIdxBR <: Int,
                R <: Int,
                C <: Int,
                T
]
      (using Submatrix.SliceOK[RowIdxTL, ColIdxTL, RowIdxBR, ColIdxBR, R, C] =:= true)
      (using
       Matrix.Requirements.NonNegativeDimensions[RowIdxBR - RowIdxTL + 1, ColIdxBR - ColIdxTL + 1]
      ):

   def submatrix(m: Matrix[R, C, T]): Matrix[RowIdxBR - RowIdxTL + 1, ColIdxBR - ColIdxTL + 1, T]

object Submatrix:

   type SliceOK[RowIdxTL <: Int,
                ColIdxTL <: Int,
                RowIdxBR <: Int,
                ColIdxBR <: Int,
                R <: Int,
                C <: Int
   ] =
      RowIdxTL >= 0 && RowIdxTL + 1 < R &&
         ColIdxTL >= 0 && ColIdxTL + 1 < C &&
         RowIdxBR >= RowIdxTL && RowIdxBR < R &&
         ColIdxBR >= ColIdxTL && ColIdxBR < C
