package matr:

  import scala.compiletime.ops.boolean.*
  import scala.compiletime.ops.int.*


  trait Submatrix[RowIdxTL <: Int, ColIdxTL <: Int, RowIdxBR <: Int, ColIdxBR <: Int, R <: Int, C <: Int, T]
                 (using Submatrix.SliceOK[RowIdxTL, ColIdxTL, RowIdxBR, ColIdxBR, R, C] =:= true)
                 (using Matrix.DimsOK[RowIdxBR - RowIdxTL + 1, ColIdxBR - ColIdxTL + 1] =:= true):

    def submatrix(m: Matrix[R, C, T]): Matrix[RowIdxBR - RowIdxTL + 1, ColIdxBR - ColIdxTL + 1, T]


  object Submatrix:

    type SliceOK[RowIdxTL <: Int, ColIdxTL <: Int, RowIdxBR <: Int, ColIdxBR <: Int, R <: Int, C <: Int] = 
      RowIdxTL >= 0 && RowIdxTL + 1 < R &&
      ColIdxTL >= 0 && ColIdxTL + 1 < C &&
      RowIdxBR >= RowIdxTL && RowIdxBR < R &&
      ColIdxBR >= ColIdxTL && ColIdxBR < C
