package matr.dflt

import matr.MatrixAddition
import matr.MatrixFactory

trait DefaultMatrixAddition:

    given defaultMatrixAddition[R <: Int, C <: Int, T](
        using
        Numeric[T],
        MatrixFactory[R, C, T]
    ): MatrixAddition[R, C, T] =
        new MatrixAddition:
            def plus(lhs: M, rhs: M): M = lhs.combine(rhs)(summon[Numeric[T]].plus)

object DefaultMatrixAddition extends DefaultMatrixAddition
