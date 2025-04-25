package matr.dflt

import matr.MatrixFactory
import matr.MatrixSubtraction

trait DefaultMatrixSubtraction:

    given defaultMatrixSubtraction[R <: Int, C <: Int, T](
        using num: Numeric[T]
    )(
        using mf: MatrixFactory[R, C, T]
    )
        : MatrixSubtraction[R, C, T] =
        new MatrixSubtraction:
            def minus(lhs: M, rhs: M): M = lhs.combine(rhs)(num.minus)

object DefaultMatrixSubtraction extends DefaultMatrixSubtraction
