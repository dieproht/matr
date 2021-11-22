package matr.impl

import matr.Matrix
import matr.MatrixFactory

private[matr] object Modify:

   inline def apply[R <: Int, C <: Int, T]
            (m: Matrix[R, C, T])
            (using mf: MatrixFactory[R, C, T])
            : MatrixFactory.Builder[R, C, T] =
      val builder = mf.builder
      m.iterate { (rowIdx, colIdx) =>
         builder(rowIdx, colIdx) = m(rowIdx, colIdx)
      }
      builder
