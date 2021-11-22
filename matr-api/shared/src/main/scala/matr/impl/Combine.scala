package matr.impl

import matr.Matrix
import matr.MatrixFactory

private[matr] object Combine:

   inline def apply[R <: Int, C <: Int, T, U, V]
            (lhs: Matrix[R, C, T], rhs: Matrix[R, C, U], op: (T, U) => V)
            (using mf: MatrixFactory[R, C, V])
            : Matrix[R, C, V] =
      val buildr = mf.builder
      lhs.iterate { (rowDim, colDim) =>
         buildr(rowDim, colDim) = op(lhs(rowDim, colDim), rhs(rowDim, colDim))
      }
      buildr.result
