package matr.impl

import matr.Matrix
import matr.MatrixFactory

private[matr] object Map:

    inline def apply[R <: Int, C <: Int, T, U](m: Matrix[R, C, T], op: T => U)(using
        mf: MatrixFactory[R, C, U]
    )
        : Matrix[R, C, U] =
        val buildr = mf.builder
        m.iterate: (rowDim, colDim) =>
            buildr(rowDim, colDim) = op(m(rowDim, colDim))
        buildr.result
