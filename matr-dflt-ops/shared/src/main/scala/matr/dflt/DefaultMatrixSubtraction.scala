package matr.dflt

import matr.MatrixFactory
import matr.MatrixSubtraction

trait DefaultMatrixSubtraction:

    given defaultMatrixSubtraction[R <: Int, C <: Int, T](
        using
        Numeric[T],
        MatrixFactory[R, C, T]
    ): MatrixSubtraction[R, C, T] =
        new MatrixSubtraction:
            def minus(lhs: M, rhs: M): M = lhs.combine(rhs)(summon[Numeric[T]].minus)

object DefaultMatrixSubtraction extends DefaultMatrixSubtraction
